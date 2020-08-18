angular.module('myApp.application.history', [])
    .controller('HistoryApplicationCtrl', function($scope, $routeParams,$window, applicationService) {
        applicationService.get_history_of_application($routeParams["id"]).then(function(value){
            $scope.data = value
            console.log(value)
        })
        $scope.dateFormat = function(date){
            const dateFormat = new Date(date)
            let dateString = ""
            if(dateFormat.getMinutes() < 10){
                dateString = dateFormat.getDate()+ "-" + dateFormat.getMonth()+ "-" +dateFormat.getFullYear() + ", " + dateFormat.getHours() + ":" + "0"+dateFormat.getMinutes()
            }
            else dateString = dateFormat.getDate()+ "-" + dateFormat.getMonth()+ "-" +dateFormat.getFullYear() + ", " + dateFormat.getHours() + ":" +  dateFormat.getMinutes()
            return dateString
        }
        $scope.back = function(){
            $window.location.href = '#!/application/reviewers/view'
        }
    })