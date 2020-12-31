const worker = angular.module('workers', [])
worker.factory('workersService', ['$http', '$q', '$window', function ($http, $q, $window) {
    return {
        getWorkersInOffice: function (id) {
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/workers/getWorkersInOffice?name=${id}`).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getWorkerById: function (id) {
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/workers/getWorkerById?id=${id}`).then(function (response) {
                if (response.status != 400) {
                    deferred.resolve(response.data)
                } else alert("Error!")
            })
            return deferred.promise
        },
        delete: function (newWorkers, officeName, deletedWorkers) {
            const requestBody = {
                newWorkers: newWorkers,
                officeName: officeName,
                deletedWorkers: deletedWorkers,
            }
            $http.post('http://localhost:8080/workers/deleteWorker', requestBody).then(function (response) {
                if (response.status === 400) {
                    alert(response.data.title)
                    $window.location.reload()
                }
            })
        },
        addWorker: function (worker, officeId) {
            const requestBody = {
                worker: worker,
                officeName: officeId,
            }
            $http.post('http://localhost:8080/workers/addWorker', requestBody).then(function (response) {
                if (response.status === 400) {
                    alert(response.data.title)
                    $window.location.reload()
                }
            })
        },
        edit: function (worker, officeName) {
            $http.post('http://localhost:8080/workers/editWorker', worker).then(function (response) {
                if (response.status === 400) {
                    alert(response.data.title)
                    $window.location.href = `#!/workers/${officeName}`
                } else {
                    $window.location.href = `#!/workers/${officeName}`
                    $window.location.reload()
                }
            })
        },
        viewTrainings: function (id) {
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/workers/viewTrainings?id=${id}`).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        }
    }
}])