angular.module('myApp.application.form', [])
    .controller('FormApplicationCtrl', function ($scope, $routeParams, $window, applicationService, officeService) {
        $scope.navigation = 0;
        $scope.confirmData = false
        $scope.step2Status = true
        $scope.errorConfirm = ''
        $scope.messageFromBack = ''
        $scope.errorAdditionalInfo = ''
        $scope.typesOfReasons = [
            {key: 1, value: "DISEASE"},
            {key: 2, value: "MEDICAL_EXAMINATION"},
            {key: 3, value: "SALARY_DELAY"},
            {key: 4, value: "UNEXPECTED_BREAKDOWN"},
        ]
        $scope.application = {
            additionalInfo: "",
            educationalProgram: '',
            groups: [
                {
                    name: "0-2 age",
                    numberOfClasses: 1,
                    activities: ''
                },
                {
                    name: "2-4 age",
                    numberOfClasses: 1,
                    activities: ""
                },
                {
                    name: "5-6 age",
                    numberOfClasses: 1,
                    activities: ''
                },
                {
                    name: "6+ age",
                    numberOfClasses: 1,
                    activities: ''
                },
            ]
        }
        officeService.getOfficeByName(routeParams["officeName"]).then(function (value) {
            if (value.title) {
                $scope.messageFromBack = value.title
            }
            $scope.office = value.office
            $scope.notPassedWorkers = value.notPassedWorkers
        })
        $scope.next = function () {
            $scope.navigation += 1;
        }
        $scope.changeConfirm = function () {
            $scope.confirmData = !$scope.confirmData
        }
        $scope.back = function () {
            $scope.navigation -= 1;
        }

        $scope.submit = function () {
            if (!$scope.confirmData) {
                $scope.errorConfirm = "Confirm your data!"
            } else if ($scope.application.additionalInfo === '') {
                $scope.errorAdditionalInfo = 'Enter additional info in step 4!'
            } else {
                $scope.notPassedReasons = []
                $scope.notPassedWorkers.map(function (value) {
                    $scope.notPassedReasons.push({
                        workerID: value.id,
                        reason: value.reason
                    })
                })
                applicationService.createApplication($scope.application, $scope.notPassedReasons, $scope.office).then(function (value) {
                    if (value.title !== 'ok!') {
                        alert(value.title)
                        $scope.return()
                    } else {
                        $scope.return()
                    }
                })
                $scope.errorConfirm = ''
                $scope.errorAdditionalInfo = ''
            }

        }
        $scope.return = function () {
            $window.location.href = '#!/main'
        }
        $scope.checkData = function () {
            let flag = true
            if ($scope.office.location === undefined) {
                $scope.errorMessage = "Back to step 1 and enter office location!"
                return false
            }
            for (let i = 0; i < $scope.notPassedWorkers.length; i++) {
                if ($scope.notPassedWorkers[i].reason === undefined) {
                    $scope.errorMessage = "Back to step 2 and enter reasons"
                    $scope.step2Status = false
                    return false
                }
            }
            $scope.step2Status = true
            if ($scope.application.educationalProgram === "") {
                $scope.errorMessage = "Back to step 3 and enter educational program!"
                return false
            }
            for (let i = 0; i < $scope.application.groups.length; i++) {
                if ($scope.application.groups[i].activities === "") {
                    $scope.errorMessage = "Back to step 3 and enter activities"
                    return false
                }
            }
            return flag
        }
    })