angular.module('myApp.workers', [])
    .controller('WorkersCtrl', function ($scope, workersService, $routeParams, $window) {

        $scope.checkAllWorkers = false

        $scope.officeName = $routeParams["officeName"]

        workersService.getWorkersInOffice($scope.officeName).then(function (value) {
            $scope.officeData = angular.copy(value)
        })

        $scope.selectedWorkers = []

        $scope.existWorker = function (worker) {
            return $scope.selectedWorkers.indexOf(worker) > -1
        }

        $scope.selectWorker = function (worker) {
            const index = $scope.selectedWorkers.indexOf(worker)

            if (index > -1) $scope.selectedWorkers.splice(index, 1)
            else $scope.selectedWorkers.push(worker)
        }

        $scope.selectAllWorkers = function () {
            if (!$scope.checkAllWorkers) {
                $scope.officeData.map(function (worker) {
                    const index = $scope.selectedWorkers.indexOf(worker)
                    if (index >= 0) return true
                    else $scope.selectedWorkers.push(worker)
                })
            } else $scope.selectedWorkers = []
        }

        $scope.addWorker = function () {
            $window.location.href = `#!/workers/add/${$scope.officeName}`
        }

        $scope.deleteWorker = function () {
            $scope.selectedWorkers.map(function (item) {
                const index = $scope.officeData.indexOf(item)
                $scope.officeData.splice(index, 1)
            })
            workersService.delete($scope.officeData, $scope.officeName, $scope.selectedWorkers)
            $window.location.reload()
        }

        $scope.editWorker = function (item) {
            $window.location.href = `#!/workers/edit/${$scope.officeName}/${item.id}`
        }

        $scope.viewTrainings = function (id) {
            $window.location.href = `#!/workers/${$scope.officeName}/trainings/${id}`
        }

    })