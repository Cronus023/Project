angular.module('myApp.office', ['ngRoute'])
    .controller('OfficeCtrl', function ($scope, $http, officeService, $window) {
        $scope.message = ''
        $scope.create = function (officeForm) {
            if (officeForm.$valid) {
                const officeBody = {
                    name: $scope.nameOfOffice,
                    location: $scope.location,
                    contact_details: $scope.contact,
                }
                officeService.create(officeBody).then(function (value) {
                    $scope.message = 'Such office already exist!'
                })
            }
        }
    });