'use strict';

angular.module('myApp.main', ['ngRoute'])
    .controller('MainCtrl', function($scope, $http) {
        $http.get('http://localhost:8080/get_office').
        then(function (response) {
            console.log(response.data)
            $scope.offices = response.data
        })

        $scope.become = function(office){
             const becomeRequest ={
                 token: localStorage.getItem('JwtToken'),
                 office:office
             }
             $http.post('http://localhost:8080/become',becomeRequest).
             then(function (response) {
                 console.log(response)
             })
        }
        $scope.check = function(office){
            if(office.leaderID.login == localStorage.getItem('UserLogin')){
                return true
            }
            else return false
        }
    });