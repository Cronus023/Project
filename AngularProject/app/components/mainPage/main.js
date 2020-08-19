'use strict';

angular.module('myApp.main', ['ngRoute'])
    .controller('MainCtrl', function($scope, officeService, $window, authService) {
        $scope.flag = false

        officeService.get_office().then(function(value){
            $scope.offices = value
            console.log(value)
        })

        $scope.become = function(office){
            officeService.become(office)
        }
        $scope.check = function(office){
            let flag = false
            if(office.leaderID.length === 0){
                return false
            }
            if(office.leaderID.length !== 0){
                office.leaderID.map(item =>{
                    if(item.login === localStorage.getItem('UserLogin')){
                        flag = true
                    }
                })
            }
            return flag
        }
        $scope.view = function(office){
            const name = office.name
            $window.location.href = `/#!/workers/${name}`
        }
        $scope.dateFormat = function(date){
            return authService.dateFormat(date)
        }


    })