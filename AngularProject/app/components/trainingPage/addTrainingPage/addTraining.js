angular.module('myApp.trainings.add', [])
    .controller('AddTrainingsCtrl', function($scope, workersService, $routeParams,$window) {
        $scope.message = ''

        $scope.add = function(trainingForm){
            if(trainingForm.$valid){
                const date = new Date()
                if($scope.dateOfTraining <=  date || $scope.endOfRegistration <=  date){
                    $scope.message = "Incorrect date"
                }
                else if($scope.dateOfTraining < $scope.endOfRegistration){
                    $scope.message = "The training date must be later than the registration date "
                }
                else {
                    const training = {
                        date: $scope.dateOfTraining,
                        dateOfEnd: $scope.endOfRegistration,
                        type: $scope.type,
                        numberOfSeats: $scope.numberOfSeats
                    }
                }
            }
        }
    })