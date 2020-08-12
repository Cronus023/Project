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
            const deferred = $q.defer()
            $http.get('http://localhost:8080/training/get_trainings').
            then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        get_workers: function(id){
            const deferred = $q.defer()

            const request = {
                login: localStorage.getItem('UserLogin'),
                id: id
            }

            $http.post(`http://localhost:8080/training/get_workers`, request).
            then(function (response) {
                console.log(response)
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        register_workers: function(id, newWorkers){
            const deferred = $q.defer()
            const request = {
                newWorkers: newWorkers,
                id: id
            }

            $http.post(`http://localhost:8080/training/register_workers`, request).
            then(function (response) {
                deferred.resolve(response.data)
                if(response.data.title != 'ok'){
                    alert('error!')
                    $window.location.reload()
                }
            })
            return deferred.promise
        },
    }
}] )