'use strict';

describe('myApp.main module', function() {
    beforeEach(module('myApp.main'))
    describe('main controller', function(){
        it('should ....', inject(function($controller) {
            //spec body
            const LoginCtrl = $controller('MainCtrl')
            expect(LoginCtrl).toBeDefined()
        }))
    })
})