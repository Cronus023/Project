const trainer = angular.module('training', [])
trainer.factory('trainingService', ['$http', '$q', '$window', function ($http, $q, $window) {
    return {
        add: function (training, userLogin) {
            const requestBody = {
                training: training,
                userLogin: userLogin
            }
            $http.post('http://localhost:8080/training/add', requestBody).then(function (response) {
                if (response.data.title === 'ok') {
                    $window.location.href = '#!/trainings'
                    $window.location.reload()
                } else alert("error")
            })
        },
        deleteTraining: function (id) {
            const deferred = $q.defer()
            $http.post(`http://localhost:8080/training/deleteTraining?id=${id}`).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        deleteWorkersInTraining: function (id, workers) {
            const deferred = $q.defer()
            const requestBody = {
                id: id,
                newWorkers: workers
            }
            $http.post(`http://localhost:8080/training/deleteWorkersInTraining`, requestBody).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        editTraining: function (training) {
            const deferred = $q.defer()
            $http.post(`http://localhost:8080/training/editTraining`, training).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getTrainingById: function (id) {
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/training/getTrainingById?id=${id}`).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getTrainings: function () {
            const deferred = $q.defer()
            $http.get('http://localhost:8080/training/getTrainings').then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        findWorkersByIdAndUserLogin: function (id) {
            const deferred = $q.defer()

            const request = {
                login: localStorage.getItem('UserLogin'),
                id: id
            }

            $http.post(`http://localhost:8080/training/findWorkersByIdAndUserLogin`, request).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        findTrainingWorkers: function (id) {
            const deferred = $q.defer()

            const request = {
                login: localStorage.getItem('UserLogin'),
                id: id
            }

            $http.post(`http://localhost:8080/training/findTrainingWorkers`, request).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        registerWorkers: function (id, newWorkers) {
            const deferred = $q.defer()
            const request = {
                newWorkers: newWorkers,
                id: id
            }

            $http.post(`http://localhost:8080/training/registerWorkers`, request).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        addVisitors: function (id, workers) {
            const deferred = $q.defer()
            const request = {
                newWorkers: workers,
                id: id
            }

            $http.post(`http://localhost:8080/training/addVisitors`, request).then(function (response) {
                console.log(response)
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        addPassedWorkers: function (id, workers) {
            const deferred = $q.defer()
            const request = {
                newWorkers: workers,
                id: id
            }

            $http.post(`http://localhost:8080/training/addPassedWorkers`, request).then(function (response) {
                console.log(response)
                deferred.resolve(response.data)
            })
            return deferred.promise
        }
    }
}])