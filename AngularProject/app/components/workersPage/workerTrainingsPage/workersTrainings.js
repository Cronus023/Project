angular.module('myApp.workers.trainings', [])
    .controller('TrainingsWorkersCtrl', function ($scope, workersService, $routeParams, authService) {
        workersService.viewTrainings($routeParams["workerID"]).then(function (value) {
            $scope.trainings = value
        })
        $scope.dateFormat = function (date) {
            return authService.dateFormat(date)
        }
    })