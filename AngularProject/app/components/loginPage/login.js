angular.module('myApp.login', ['ngRoute'])
    .controller('LoginCtrl', function($scope, authService, $window) {
        $scope.login = ""
        $scope.password = ""

        $scope.auth = function(loginForm)
        {
            if(loginForm.$valid){
                const authBody = {
                    login: $scope.login,
                    password: $scope.password
                }
                const response = authService.login(authBody)
                response.then(function(value){
                    if(value == 'ok'){
                        $window.location.href = "/#!/main"
                        $window.location.reload();
                    }
                    else $scope.message = value
                })
            }
        }
    });