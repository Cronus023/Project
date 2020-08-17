angular.module('myApp.application.reviewers.regular', [])
    .controller('RegularReviewersApplicationCtrl', function($scope, $routeParams,$window, applicationService) {
        applicationService.get_application_by_id($routeParams["id"]).then(function(value){
            $scope.applicationData = value.application
            $scope.workers = value.workers
        })
        $scope.section = 1
        $scope.selectGroups = function(){
            $scope.section = 1
        }
        $scope.selectWorkers = function(){
            $scope.section = 2
        }
        $scope.rejectAndAccept = function(status){
            applicationService.reject_accept_application($routeParams["id"],$scope.applicationData, status).then(function(value){
                console.log(value)
            })
        }
    })