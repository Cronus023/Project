angular.module('myApp.trainings.add', [])
    .controller('AddTrainingsCtrl', function ($scope, trainingService, $routeParams, $window) {
        $scope.message = ''
        $scope.typesOfTraining = [
            {key: 1, value: "PSYCHOLOGICAL"},
            {key: 2, value: "PHYSICAL"},
            {key: 3, value: "PERSONAL_GROWTH"},
            {key: 4, value: "COMMUNICATION"},
        ]
        $scope.userLogin = $routeParams["userLogin"]
        $scope.add = function (trainingForm) {
            if (trainingForm.$valid) {
                const date = new Date()
                if ($scope.type == null) {
                    $scope.message = "Choose type"
                } else if ($scope.dateOfTraining <= date || $scope.endOfRegistration <= date) {
                    $scope.message = "Incorrect date"
                } else if ($scope.dateOfTraining < $scope.endOfRegistration) {
                    $scope.message = "The training date must be later than the registration date "
                } else {
                    const training = {
                        date: $scope.dateOfTraining,
                        dateOfEnd: $scope.endOfRegistration,
                        type: $scope.type,
                        numberOfSeats: $scope.numberOfSeats
                    }
                    trainingService.add(training, $scope.userLogin)
                }
            }
        }
    })