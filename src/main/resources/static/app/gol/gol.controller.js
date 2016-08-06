angular.module('gol.grid')
.controller('golController', ["$http", function($http) {
    var vm = this;
    vm.rowNumber = 20;
    vm.colNumber = 20;
    vm.running = false;

    vm.init = init;
    vm.addCell = addCell;
    vm.nextRound = nextRound;
    vm.play = play;
    vm.stop = stop;

    init();

    function init(){
        $http({
              method: 'POST',
              url: '/gol/init',
              data: {
                row: vm.rowNumber,
                col: vm.colNumber
              }
        }).then(function successCallback(response) {
            vm.gridRow = new Array(vm.rowNumber);
            vm.gridCol = new Array(vm.colNumber);
            vm.grid=[];
        });
    };

    function addCell(row, col) {
        $http({
          method: 'POST',
          url: '/gol/',
          data: {
            row: row,
            col: col
          }
        }).then(function successCallback(response) {
            getAliveCell();
        });
    };

    function getAliveCell() {
        $http({
          method: 'GET',
          url: '/gol'
        }).then(function successCallback(response) {
            vm.grid= new Array(vm.gridRow.length);
            for (var i = 0; i < vm.gridRow.length; i++) {
              vm.grid[i] = new Array(vm.gridCol.length);
            }
            response.data.forEach(function(elem){
                var row = elem.split("/")[0];
                var col = elem.split("/")[1];

                vm.grid[parseInt(row)][parseInt(col)] = true;
            });
        });
    };

    function nextRound() {
        $http({
          method: 'PUT',
          url: '/gol'
        }).then(function successCallback(response) {
            getAliveCell();
        });
    };

    function play() {
        vm.running = true;
        vm.interval = setInterval(function(){
            nextRound();
        }, 1000);
    };

    function stop() {
        vm.running = false;
        clearInterval(vm.interval);
    }
}]);