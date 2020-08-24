const authGuard = angular.module('myApp.guards.auth', [])
authGuard.factory('authGuard',['$http','$q','$window', '$route','authService', function($http,  $q, $window, authService){
    return{
        checkLogin: function(){
            const token = localStorage.getItem('JwtToken')
            $http.get(`http://localhost:8080/auth/check?token=${token}`).
            then(function (response) {
                if(response.status === 400){
                    $window.location.href = '#!/login'
                }
            })
        },
        checkRedirect: function(){
            const token = localStorage.getItem('JwtToken')
            if(token != null){
                const role = localStorage.getItem('UserRole')
                if(role === 'PROVIDER'){
                    $window.location.href = '#!/main'
                    $window.location.reload()
                }
                if(role === 'CURRICULUM_REVIEWER' || role === 'REGULAR_REVIEWER' ||  role === 'SUPERVISION' ){
                    $window.location.href = '#!/application/reviewers/view'
                    $window.location.reload()
                }
                if(role === 'TRAINING_OPERATOR'){
                    $window.location.href = '#!/trainings'
                    $window.location.reload()
                }
            }
        }
    }
}] )