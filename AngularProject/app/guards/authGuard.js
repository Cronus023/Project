const authGuard = angular.module('myApp.guards.auth', [])
authGuard.factory('authGuard',['$http','$q','$window', function($http,  $q, $window){
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
    }
}] )