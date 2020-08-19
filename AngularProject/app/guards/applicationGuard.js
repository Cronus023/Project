const applicationGuard = angular.module('myApp.guards.application', [])
applicationGuard.factory('applicationGuard',['$http','$q','$window', '$route', function($http,  $q, $window, $route){
    return{
        checkUserRole: function(){
            const role = localStorage.getItem('UserRole')
            if(role !== 'REGULAR_REVIEWER' && role !== 'SUPERVISION' && role !== 'CURRICULUM_REVIEWER'){
                alert('Your current role is ' +  role + ',' + 'change it to REGULAR_REVIEWER or SUPERVISION or CURRICULUM_REVIEWER before entering this page')
                $window.location.href = '#!/changeRole'
                $window.location.reload()
            }
        },
        checkUserRole_SUPERVISION: function(){
            const role = localStorage.getItem('UserRole')
            if(role !== 'SUPERVISION'){
                alert('Your current role is ' +  role + ',' + 'change it to SUPERVISION before entering this page')
                $window.location.href = '#!/changeRole'
                $window.location.reload()
            }
        },
        checkUserRole_REGULAR_REVIEWER: function(){
            const role = localStorage.getItem('UserRole')
            if(role !== 'SUPERVISION' && role !== 'REGULAR_REVIEWER'){
                alert('Your current role is ' +  role + ',' + 'change it to SUPERVISION or REGULAR_REVIEWER before entering this page')
                $window.location.href = '#!/changeRole'
                $window.location.reload()
            }
        },
        checkUserRole_CURRICULUM_REVIEWER: function(){
            const role = localStorage.getItem('UserRole')
            if(role !== 'SUPERVISION' && role !== 'CURRICULUM_REVIEWER'){
                alert('Your current role is ' +  role + ',' + 'change it to SUPERVISION or CURRICULUM_REVIEWER before entering this page')
                $window.location.href = '#!/changeRole'
                $window.location.reload()
            }
        },

        checkApplicationId: function(){
            const role = localStorage.getItem('UserRole')
            if(role === 'REGULAR_REVIEWER' || role === 'SUPERVISION' || role === 'CURRICULUM_REVIEWER'){
                $http.get(`http://localhost:8080/guards/check_application?id=${$route.current.params.applicationID}`).
                then(function (response) {
                    const data  = response.data
                    if(data.title !== 'ok!'){
                        alert(data.title)
                        $window.location.href = '#!/application/reviewers/view'
                        $window.location.reload()
                    }
                    console.log(data)
                })
            }
        },
        checkApplicationHistoryPage: function(){
            this.checkUserRole()
            this.checkApplicationId()
        },
        checkFinalDecisionPage: function(){
            this.checkUserRole_SUPERVISION()
            this.checkApplicationId()
        },
        check_REGULAR_REVIEWER_Page: function(){
            this.checkUserRole_REGULAR_REVIEWER()
            this.checkApplicationId()
        },
        check_CURRICULUM_REVIEWER_Page: function(){
            this.checkUserRole_CURRICULUM_REVIEWER()
            this.checkApplicationId()
        },
    }
}] )