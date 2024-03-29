'use strict'
angular.module('myApp.register', ['ngRoute'])
    .controller('RegisterCtrl', function ($scope, authService, $window) {

        $scope.typesOfRoles = [
            {key: 1, value: "PROVIDER"},
            {key: 2, value: "CURRICULUM_REVIEWER"},
            {key: 3, value: "REGULAR_REVIEWER"},
            {key: 4, value: "SUPERVISION"},
            {key: 5, value: "TRAINING_OPERATOR"},
        ]

        $scope.errorMessage = ''

        $scope.registration = function (registerForm) {
            if (registerForm.$valid) {
                if (!$scope.role) {
                    $scope.errorMessage = "Choose your role!"
                } else {
                    const registerBody = {
                        login: $scope.login,
                        password: $scope.password,
                        name: $scope.name,
                        surname: $scope.surname,
                        address: $scope.adress,
                        education: $scope.education,
                        email: $scope.mail,
                        phone: $scope.phone,
                        roles: [$scope.role]
                    }
                    const response = authService.registration(registerBody)
                    response.then(function (value) {
                        $scope.errorMessage = value
                        if (value === "") {
                            $window.location.href = '#!/login'
                        }
                    })

                }
            }
        }

    })