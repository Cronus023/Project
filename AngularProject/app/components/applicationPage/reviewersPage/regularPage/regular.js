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
    })