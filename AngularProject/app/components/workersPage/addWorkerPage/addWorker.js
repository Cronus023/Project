angular.module('myApp.workers.addWorker', [])
    .controller('AddWorkerCtrl', function($scope, workersService, $routeParams,$window) {
        $scope.message = ''
        $scope.checkAll = false
        $scope.id = $routeParams["id"]

        $scope.add = function(workerForm){
            if(workerForm.$valid){
                const worker = {
                    name : $scope.name,
                    education: $scope.education,
                    email: $scope.email,
                }
                workersService.add(worker,$scope.id)
            }
        }
        $scope.back = function(){
            $window.location.href = `#!/workers/${$scope.id}`
            $window.location.reload()
        }
    })