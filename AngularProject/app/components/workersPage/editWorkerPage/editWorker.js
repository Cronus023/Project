angular.module('myApp.workers.edit', [])
    .controller('EditWorkersCtrl', function ($scope, workersService, $routeParams) {

        $scope.workerID = $routeParams["workerID"]
        $scope.officeName = $routeParams["officeName"]

        workersService.getWorkerById($scope.workerID).then(function (value) {
            $scope.name = value.name
            $scope.education = value.education
            $scope.email = value.email
            $scope.worker = value
        })

        $scope.edit = function (workerForm) {
            if (workerForm.$valid) {
                workersService.edit($scope.worker, $scope.officeName)
            }
        }

    })