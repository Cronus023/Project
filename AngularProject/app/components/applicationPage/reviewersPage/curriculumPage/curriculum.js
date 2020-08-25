angular.module('myApp.application.reviewers.curriculum', [])
    .controller('CurriculumReviewersApplicationCtrl', function ($scope, $routeParams, $window, applicationService) {
        applicationService.getEducationalProgramById($routeParams["applicationID"]).then(function (value) {
            $scope.applicationData = value.application
            $scope.curriculumStatus = false

            $scope.responses = value.responses
            $scope.responses.map(function (item) {
                const applicationId = item.applicationID.id.toString()
                if (item.typeOfSection === 'CURRICULUM' && applicationId === $routeParams["applicationID"]) {
                    $scope.curriculumStatus = true
                }
            })
        })

        $scope.rejectAndAccept = function (status) {
            const section = 'CURRICULUM'
            applicationService.rejectAndAccept($routeParams["applicationID"], $scope.applicationData, status, section).then(function (value) {
                if (value.title !== 'ok!') {
                    alert(value.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                $window.location.reload()
            })
        }
    })