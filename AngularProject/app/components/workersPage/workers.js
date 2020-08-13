angular.module('myApp.workers', [])
    .controller('WorkersCtrl', function($scope, workersService, $routeParams,$window) {
        $scope.checkAll = false

        $scope.id = $routeParams["id"]
        workersService.get_workers($scope.id).then(function(value){
            $scope.data = value
        })

        $scope.selected = []
        $scope.exist = function(item){
            return $scope.selected.indexOf(item) > -1
        }
        $scope.select = function(item){
            const index = $scope.selected.indexOf(item)
            if(index > -1){
                $scope.selected.splice(index, 1)
            }
            else $scope.selected.push(item)
        }
        $scope.selectAll = function(){
            if(!$scope.checkAll){
                $scope.data.map(function(item){
                    const index = $scope.selected.indexOf(item)
                    if(index >=0){
                        return true
                    }
                    else{
                        $scope.selected.push(item)
                    }
                })
            }
            else{
                $scope.selected = []
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
            workersService.delete($scope.data, $scope.id, $scope.selected)
        }

        $scope.edit = function(item){
            $window.location.href = `#!/workers/edit/${$scope.id}/${item.id}`
        }
        $scope.viewTrainings = function(id){
            $window.location.href = `#!/workers/trainings/${id}`
        }

    })