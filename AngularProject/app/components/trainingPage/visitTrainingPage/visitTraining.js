angular.module('myApp.trainings.visit', [])
    .controller('VisitTrainingsCtrl', function($scope, trainingService,$routeParams, $window) {
        $scope.message = ''
        $scope.messageOfAdd = ''
        $scope.checkAllVisit = false
        $scope.checkAllPassing  = false

        trainingService.get_visit_and_passing($routeParams["id"]).then(function(value){
            if(value.title){
                $scope.message = value.title
            }
            else{
                $scope.data = value.trainingWorkers
                $scope.training = value.training
            }
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
                    if(index >=0 || $scope.checkVisiting(item)){
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
            $scope.checkAllVisit = !$scope.checkAllVisit
        }

        $scope.selectAllPassing = function(){
            if(!$scope.checkAllPassage){
                $scope.data.map(function(item){
                    const index = $scope.selectedForPassing.indexOf(item)
                    if(index >=0 || $scope.checkPassing(item)){
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
            $scope.checkAllPassage = !$scope.checkAllPassage
        }
        $scope.add_visitors = function(){
            trainingService.add_visitors($routeParams["id"], $scope.selectedForVisit).then(function(value){
                if (value.title === 'ok!'){
                    $scope.messageOfAdd = 'Workers successfully marked!'
                    setTimeout(function(){
                        $window.location.reload()
                    }, 1000)
                }
                else{
                    $scope.messageOfAdd = 'error'
                    setTimeout(function(){
                        $scope.messageOfAdd = ''
                    }, 1000)
                }
            })
        }
        $scope.add_passed = function(){
            trainingService.add_passed($routeParams["id"], $scope.selectedForPassing).then(function(value){
                if (value.title === 'ok!'){
                    $scope.messageOfAdd = 'Workers successfully passed!'
                    setTimeout(function(){
                        $window.location.reload()
                    }, 1000)
                }
                else{
                    $scope.messageOfAdd = 'error'
                    setTimeout(function(){
                        $scope.messageOfAdd = ''
                    }, 1000)
                }
            })
        }
        $scope.checkVisiting = function(worker){
            let flag = false
            $scope.training.trainingVisitorsID.filter(function(item) {
                if(worker.id == item.id){
                    flag = true
                }
            })
            return flag
        }
        $scope.checkPassing = function(worker){
            let flag = false
            $scope.training.trainingPassedID.filter(function(item) {
                if(worker.id == item.id){
                    flag = true
                }
            })
            return flag
        }
    })


