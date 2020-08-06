'use strict';

angular.module('myApp.main', ['ngRoute'])
    .controller('MainCtrl', function($scope, $http) {
        $http.get('http://localhost:8080/main').
        then(function success(response) {
            console.log(response.data)
            $scope.users = response.data
        })
    });