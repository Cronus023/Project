angular.module('myApp.workers.addWorker', [])
    .controller('AddWorkerCtrl', function($scope, workersService, $routeParams,$window) {
        $scope.message = ''
        $scope.checkAll = false
        $scope.id = $routeParams["workerID"]

        $scope.add = function(workerForm){
            if(workerForm.$valid){
                const worker = {
                    name : $scope.nameOfWorker,
                    education: $scope.education,
                    email: $scope.email,
                }
                workersService.add(worker,$scope.id)
                $scope.message = 'Worker successfully added!'

                setTimeout(function(){
                    $scope.message = ''
                }, 1000)
            }
        }

        $scope.back = function(){
            $window.location.href = `#!/workers/${$scope.id}`
            $window.location.reload()
        }
    })