const trainer = angular.module('training', [])
trainer.factory('trainingService',['$http','$q','$window', function($http,  $q, $window){
    return{
        add: function(training, userLogin){
            const requestBody = {
                training: training,
                userLogin:userLogin
            }
            $http.post('http://localhost:8080/training/add', requestBody).
            then(function (response) {
                if(response.data.title == 'ok'){
                    $window.location.href = '#!/trainings'
                    $window.location.reload()
                }
                else alert("error")
            })
        },

        get_trainings: function(){
            $http.get('http://localhost:8080/training/get_trainings').
            then(function (response) {
                console.log(response)
            })
        },
    }
}] )