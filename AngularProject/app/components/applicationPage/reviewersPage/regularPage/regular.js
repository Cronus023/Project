angular.module('myApp.application.reviewers.regular', [])
    .controller('RegularReviewersApplicationCtrl', function($scope, $routeParams,$window, applicationService) {
        applicationService.get_application_by_id($routeParams["id"]).then(function(value){
            $scope.applicationData = value.application
            $scope.workers = value.workers
            $scope.responses = value.responses
            $scope.responses.map(function(item){
                if(item.typeOfSection === 'GROUPS'){
                    $scope.section.groupStatus = true
                }
                if(item.typeOfSection === 'WORKERS'){
                    $scope.section.workersStatus = true
                }
                console.log(item)
            })
        })
        $scope.section = {
            number: 1,
            groupStatus: false,
            workersStatus: false,
        }
        $scope.selectGroups = function(){
            $scope.section.number = 1
        }
        $scope.selectWorkers = function(){
            $scope.section.number = 2
        }
        $scope.rejectAndAccept = function(status){
            let section = "";
            if($scope.section.number === 1){
                section = 'GROUPS'
            }
            if($scope.section.number === 2){
                section = 'WORKERS'
            }
            applicationService.reject_accept_application($routeParams["id"],$scope.applicationData, status, section).then(function(value){
                if(value.title){
                    alert(value.title)
                }
                $window.location.reload()
            })
        }
    })