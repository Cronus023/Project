angular.module('myApp.trainings.control.workers', [])
    .controller('WorkersControlTrainingsCtrl', function ($scope, trainingService, $routeParams, $window) {
        $scope.checkAll = false
        $scope.message = ''
        $scope.id = $routeParams["trainingID"]
        trainingService.getTrainingById($scope.id).then(function (value) {
            $scope.training = angular.copy(value)
            $scope.workers = $scope.training.workerID
        })

        $scope.selected = []
        $scope.exist = function (item) {
            return $scope.selected.indexOf(item) > -1
        }
        $scope.select = function (item) {
            const index = $scope.selected.indexOf(item)
            if (index > -1) {
                $scope.selected.splice(index, 1)
            } else $scope.selected.push(item)
        }
        $scope.selectAll = function () {
            if (!$scope.checkAll) {
                $scope.workers.map(function (item) {
                    const index = $scope.selected.indexOf(item)
                    if (index >= 0) {
                        return true
                    } else {
                        $scope.selected.push(item)
                    }
                })
            } else {
                $scope.selected = []
            }
        }
        $scope.delete = function () {
            trainingService.deleteWorkersInTraining($scope.id, angular.copy($scope.selected)).then(function (value) {
                $scope.message = value.title
                if ($scope.message === 'ok!') {
                    setTimeout(function () {
                        $window.location.reload()
                    }, 1000)
                } else {
                    setTimeout(function () {
                        $scope.message = ''
                    }, 1000)
                }
            })
        }
    })


