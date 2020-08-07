const auth = angular.module('auth', [])
auth.factory('authService',['$http','$q','$window', function($http,  $q, $window){
    return{
        checkLogin: function(){
            if(localStorage.getItem('JwtToken') == null){
                return false
            }
            else return true
        },
        login:function(authBody){
            const deferred = $q.defer();

            $http.post('http://localhost:8080/login',authBody).
                then(function success(response) {
                    deferred.resolve(response.data.title);
                    if(response.data.title!="") {
                        localStorage.setItem('JwtToken', response.data.title)
                        localStorage.setItem('UserLogin', authBody.login)
                    }
                })
            return deferred.promise;
        },
        registration:function(registerBody){
            const deferred = $q.defer();
            $http.post('http://localhost:8080/register',registerBody).
            then(function (response) {
                deferred.resolve(response.data.title);
            })
            return deferred.promise;
        },
        logout:function(){
            localStorage.removeItem('JwtToken')
            $window.location.href = '#!/login'
            $window.location.reload();
        }
    }
}] )