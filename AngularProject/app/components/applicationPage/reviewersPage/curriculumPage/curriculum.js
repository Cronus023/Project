angular.module('myApp.application.reviewers.curriculum', [])
    .controller('CurriculumReviewersApplicationCtrl', function($scope, $routeParams,$window, applicationService) {
        applicationService.get_educational_program_by_id($routeParams["id"]).then(function(value){
            $scope.applicationData = value.application
            $scope.curriculumStatus = false

            $scope.responses = value.responses
            $scope.responses.map(function(item){
                const applicationId = item.applicationID.id.toString()
                if(item.typeOfSection === 'CURRICULUM' && applicationId === $routeParams["id"]){
                    $scope.curriculumStatus = true
                }
            })
        })

        $scope.rejectAndAccept = function(status){
            const section = 'CURRICULUM'
            applicationService.reject_accept_application($routeParams["id"],$scope.applicationData, status, section).then(function(value){
                if(value.title !== 'ok!'){
                    alert(value.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                $window.location.reload()
            })
        }
    })