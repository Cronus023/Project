const providerGuard = angular.module('myApp.guards.provider', [])
providerGuard.factory('providerGuard',['$http','$q','$window', '$route', function($http,  $q, $window, $route){
    return{
        checkUserRole: function(){
            const role = localStorage.getItem('UserRole')
            if(role !== 'PROVIDER'){
                alert('Your current role is ' +  role + ',' + 'change it to PROVIDER before entering this page')
                $window.location.href = '#!/changeRole'
                $window.location.reload()
            }
        },
        checkMyApplicationsPage: function(){
            this.checkUserRole()
            const role = localStorage.getItem('UserRole')
            if(role === 'PROVIDER'){
                const login = localStorage.getItem('UserLogin')
                if($route.current.params.userLogin !== login){
                    alert('You cannot view applications of another PROVIDER!')
                    $window.location.href = '#!/main'
                    $window.location.reload()
                }
            }
        },
        checkFormApplicationPage: function(){
            this.checkUserRole()
            const check = 'checkForm'
            this.checkWorkersPage(check)
        },
        checkApplicationsPage: function(){
            this.checkUserRole()
            const role = localStorage.getItem('UserRole')
            if(role === 'PROVIDER'){
                const login = localStorage.getItem('UserLogin')
                if($route.current.params.userLogin !== login){
                    alert('You cannot add applications on behalf of another PROVIDER!')
                    $window.location.href = '#!/main'
                    $window.location.reload()
                }
            }
        },
        checkWorkersPage: function(check){
            this.checkUserRole()
            const role = localStorage.getItem('UserRole')
            if(role === 'PROVIDER'){
                const login = localStorage.getItem('UserLogin')
                $http.get(`http://localhost:8080/guards/check_provider_office?login=${login}&officeName=${$route.current.params.officeName}`).
                then(function (response) {
                    const data  = response.data
                    if(data.title !== 'ok!'){
                        if(data.title === 'bad!'){
                            if(check === 'checkForm'){
                                alert('You can not make form to this office')
                                $window.location.href = '#!/main'
                                $window.location.reload()
                            }
                            else{
                                alert('You can not view and change workers of this office')
                                $window.location.href = '#!/main'
                                $window.location.reload()
                            }
                        }
                        if(response.status === 400){
                            if(data.title === 'badStatus1'){
                                alert("Such office does not exist")
                                $window.location.href = '#!/main'
                                $window.location.reload()
                            }
                            if(data.title === 'badStatus2'){
                                alert("Wrong user login!")
                                localStorage.setItem('JwtToken', null)
                                localStorage.setItem('UserLogin', null)
                                localStorage.setItem('UserRole', null)
                                $window.location.href = '#!/login'
                            }
                        }
                    }
                })
            }
        },
        checkWorkerId: function(){
            $http.get(`http://localhost:8080/guards/check_worker_id?id=${$route.current.params.workerID}`).
            then(function (response) {
                const data  = response.data
                if(response.status === 400){
                    alert(data.title)
                    $window.location.href = '#!/main'
                    $window.location.reload()
                }
            })
        },
        
        checkWorkersTrainingsAndEditPages: function(){
            this.checkUserRole()
            this.checkWorkersPage()
            this.checkWorkerId()
        },



    }
}] )