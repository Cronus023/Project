'use strict';
describe('myApp.trainings module', function() {
    beforeEach(module('myApp.trainings'))
    describe('trainings controller', function(){
        it('should ....', inject(function($controller) {
            //spec body
            const LoginCtrl = $controller('TrainingsCtrl')
            expect(LoginCtrl).toBeDefined()
        }))
    })
})