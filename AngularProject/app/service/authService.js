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
            const deferred = $q.defer()

            $http.post('http://localhost:8080/login',authBody).
                then(function success(response) {
                    if(response.status == 400){
                        deferred.resolve(response.data.title)
                    }
                    else{
                        deferred.resolve('ok')
                        localStorage.setItem('JwtToken', response.data.token)
                        localStorage.setItem('UserLogin', authBody.login)
                        localStorage.setItem('UserRole', response.data.roles[0])
                    }
                })
            return deferred.promise
        },
        registration:function(registerBody){
            const deferred = $q.defer();
            $http.post('http://localhost:8080/register',registerBody).
            then(function (response) {
                deferred.resolve(response.data.title);
            })
            return deferred.promise
        },
        logout:function(){
            localStorage.removeItem('JwtToken')
            localStorage.removeItem('UserLogin')
            localStorage.removeItem('UserRole')

            $window.location.href = '#!/login'
            $window.location.reload()
        }
    }
}] )