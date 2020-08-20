angular.module('myApp.workers', [])
    .controller('WorkersCtrl', function($scope, workersService, $routeParams,$window) {
        $scope.checkAll = false

        $scope.id = $routeParams["officeName"]

        workersService.get_workers($scope.id).then(function(value){
            $scope.data = value
        })

        $scope.selectedWorkers = []

        $scope.existWorker = function(item){
            return $scope.selectedWorkers.indexOf(item) > -1
        }

        $scope.selectWorker = function(item){
            const index = $scope.selectedWorkers.indexOf(item)
            if(index > -1){
                $scope.selectedWorkers.splice(index, 1)
            }
            else $scope.selectedWorkers.push(item)
        }

        $scope.selectAll = function(){
            if(!$scope.checkAll){
                $scope.data.map(function(item){
                    const index = $scope.selectedWorkers.indexOf(item)
                    if(index >=0){
                        return true
                    }
                    else{
                        $scope.selectedWorkers.push(item)
                    }
                })
            }
            else{
                $scope.selectedWorkers = []
            }
        }

        $scope.add = function(){
            $window.location.href = `#!/workers/add/${$scope.id}`
        }

        $scope.delete = function(){
            $scope.selected.map(function(item){
                const index = $scope.data.indexOf(item)
                $scope.data.splice(index, 1)
            })
            workersService.delete($scope.data, $scope.id, $scope.selectedWorkers)
            $window.location.reload()
        }

        $scope.edit = function(item){
            $window.location.href = `#!/workers/edit/${$scope.id}/${item.id}`
        }

        $scope.viewTrainings = function(id){
            $window.location.href = `#!/workers/${$scope.id}/trainings/${id}`
        }

    })