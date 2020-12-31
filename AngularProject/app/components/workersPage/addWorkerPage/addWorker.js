angular.module('myApp.workers.addWorker', [])
    .controller('AddWorkerCtrl', function ($scope, workersService, $routeParams, $window) {

        $scope.goodMessage = ''
        $scope.officeName = $routeParams["officeName"]

        $scope.addWorker = function (workerForm) {
            if (workerForm.$valid) {
                const worker = {
                    name: $scope.nameOfWorker,
                    education: $scope.education,
                    email: $scope.email,
                }

                workersService.addWorker(worker, $scope.officeName)
                $scope.goodMessage = 'Worker successfully added!'

                setTimeout(function () {
                    $scope.goodMessage = ''
                }, 1000)
            }
        }

        $scope.back = function () {
            $window.location.href = `#!/workers/${$scope.officeName}`
            $window.location.reload()
        }
    })