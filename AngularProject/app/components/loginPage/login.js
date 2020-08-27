angular.module('myApp.login', ['ngRoute'])
    .controller('LoginCtrl', function ($scope, authService) {

        $scope.auth = function (loginForm) {
            if (loginForm.$valid) {
                const authBody = {
                    login: $scope.login,
                    password: $scope.password
                }
                authService.login(authBody).then(function (value) {
                    if (value.title) $scope.errorMessage = value.title
                    else {
                        localStorage.setItem('UserRole', value.roles[0])
                        localStorage.setItem('JwtToken', value.token)
                        localStorage.setItem('UserLogin', authBody.login)
                        authService.userNavigation(value.roles[0])
                    }
                })
            }
        }
    })