angular.module('myApp.application.finalDecision', [])
    .controller('FinalDecisionApplicationCtrl', function($scope, $routeParams,$window, applicationService) {
        $scope.finalDecision = function(decision){
            applicationService.make_final_decision($routeParams["id"], decision).then(function(value){
                console.log(value)
            })
        }
    })