'use strict';
describe('myApp.application module', function() {
    beforeEach(module('myApp.application'))
    describe('application controller', function(){
        it('should ....', inject(function($controller) {
            //spec body
            const LoginCtrl = $controller('ApplicationCtrl')
            expect(LoginCtrl).toBeDefined()
        }))
    })
})