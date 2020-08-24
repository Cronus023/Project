'use strict';
describe('myApp.application.my module', function () {
    beforeEach(module('myApp.application.my'))
    describe('application.my controller', function () {
        it('should ....', inject(function ($controller) {
            //spec body
            const MyApplicationCtrl = $controller('MyApplicationCtrl')
            expect(MyApplicationCtrl).toBeDefined()
        }))
    })
})