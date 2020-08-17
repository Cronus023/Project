angular.module('myApp.application.reviewers.curriculum', [])
    .controller('CurriculumReviewersApplicationCtrl', function($scope, $routeParams,$window, applicationService) {
        applicationService.get_educational_program_by_id($routeParams["id"]).then(function(value){
            $scope.educationalProgram = value.educationalProgram
        })
    })