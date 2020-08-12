angular.module('myApp.trainings.visit', [])
    .controller('VisitTrainingsCtrl', function($scope, trainingService,$routeParams, $window) {
        $scope.trainingId = $routeParams["id"]
        $scope.message = ''
        trainingService.get_visit_and_passing($scope.trainingId).then(function(value){
            if(value.body.title){
                $scope.message = value.body.title
            }
            else $scope.data = value.body
        })

        $scope.selectedForVisit = []
        $scope.selectedForPassing = []

        $scope.return = function(){
            $window.location.href = `#!/trainings/`
        }
        $scope.existVisit = function(item){
            return $scope.selectedForVisit.indexOf(item) > -1
        }
        $scope.existPassing = function(item){
            return $scope.selectedForPassing.indexOf(item) > -1
        }

        $scope.selectVisit = function(item){
            const index = $scope.selectedForVisit.indexOf(item)
            if(index > -1){
                $scope.selectedForVisit.splice(index, 1)
            }
            else $scope.selectedForVisit.push(item)
        }

        $scope.selectPassing = function(item){
            const index = $scope.selectedForPassing.indexOf(item)
            if(index > -1){
                $scope.selectedForPassing.splice(index, 1)
            }
            else $scope.selectedForPassing.push(item)
        }

        $scope.selectAllVisit = function(){
            if(!$scope.checkAllVisit){
                $scope.data.map(function(item){
                    const index = $scope.selectedForVisit.indexOf(item)
                    if(index >=0){
                        return true
                    }
                    else{
                        $scope.selectedForVisit.push(item)
                    }
                })
            }
            else{
                $scope.selectedForVisit = []
            }
        }

        $scope.selectAllPassing = function(){
            if(!$scope.checkAllPassage){
                $scope.data.map(function(item){
                    const index = $scope.selectedForPassing.indexOf(item)
                    if(index >=0){
                        return true
                    }
                    else{
                        $scope.selectedForPassing.push(item)
                    }
                })
            }
            else{
                $scope.selectedForPassing = []
            }
        }
        $scope.register = function(){
            console.log($scope.selectedForPassing)
            console.log($scope.selectedForVisit)

        }

    })