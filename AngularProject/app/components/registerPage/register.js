'use strict'

angular.module('myApp.register', ['ngRoute'])
    .controller('RegisterCtrl', function($scope, authService) {
        $scope.message = ''
        $scope.registration = function(registerForm)
        {
            if(registerForm.$valid){
                const registerBody = {
                    login: $scope.login,
                    password: $scope.password,
                    name: $scope.name,
                    surname: $scope.surname,
                    adress: $scope.adress,
                    education: $scope.education,
                    email: $scope.mail,
                }
                console.log(registerBody)
                const response = authService.registration(registerBody)
                response.then(function(value){
                    $scope.message = value
                })
            }
        }
    })