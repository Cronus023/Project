angular.module('myApp.application.reviewers.regular', [])
    .controller('RegularReviewersApplicationCtrl', function ($scope, $routeParams, $window, applicationService) {

        applicationService.getApplication($routeParams["applicationID"]).then(function (value) {
            $scope.applicationData = value.application
            $scope.workers = value.workers
            $scope.responses = value.responses
            $scope.responses.map(function (item) {
                const applicationId = item.applicationID.id.toString()

                if (item.typeOfSection === 'GROUPS' && applicationId === $routeParams["applicationID"]) {
                    $scope.section.groupStatus = true
                } else if (item.typeOfSection === 'WORKERS' && applicationId === $routeParams["applicationID"]) {
                    $scope.section.workersStatus = true
                }
            })
        })

        $scope.section = {
            number: 1,
            groupStatus: false,
            workersStatus: false,
        }

        $scope.selectGroups = function () {
            $scope.section.number = 1
        }

        $scope.selectWorkers = function () {
            $scope.section.number = 2
        }

        $scope.rejectAndAccept = function (status) {
            let section = ""
            if ($scope.section.number === 1) {
                section = 'GROUPS'
            }
            if ($scope.section.number === 2) {
                section = 'WORKERS'
            }
            applicationService.rejectAndAccept($routeParams["applicationID"], $scope.applicationData, status, section).then(function (value) {
                if (value.title !== 'ok!') {
                    alert(value.title)
                    $window.location.href = '#!/application/reviewers/view'
                }
                $window.location.reload()
            })
        }

    })