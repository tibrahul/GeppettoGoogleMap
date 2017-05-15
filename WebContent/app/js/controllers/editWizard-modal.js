/**
 * Since Geppetto v.90
 * Created: July 15, 2015
 * @author Pravin
 * 
 * Note: This controller will handle all the modal's related to the activity
 */

app.controller('editWizardModalCtrl',
		['$scope', '$log', '$modalInstance', 'modalDataObj','$http','RestURL','$filter',
		 function($scope, $log, $modalInstance, modalDataObj,$http,RestURL,$filter){
			
	var self=$scope;
	
	/**
	 * reference for external scope object for ui grid 
	 */
	self.scope=$scope;
	
	/**
	 * close handler for modal
	 */
	self.close=function(){
		  $modalInstance.dismiss('cancel');	
	};	
	
	self.modalObj={
			/**
			 * grid options used to display the device types
			 */
			gridOptions:{},
			title:'',
			type:'',
			selectedDevice:null,
			screens:[],
			editCallback:null,
			deleteCallback:null,
			action:''
		};
	/**
	 * deletable entity
	 */
	self.deleteEntity=null;
	/**
	 * 
	 */
	self.init=function(){
		
		//$log.debug('initializing activity modal controller! - ', type);
		//$log.debug('modal data object:',angular.toJson(modalDataObj));
		//self.screens=modalDataObj.screens;
		self.wizards=modalDataObj.wizard;
		self.actionButtons='<div style="text-align:center;"><button class="btn btn-success btn-sm" ng-click="grid.appScope.editCallback(grid,row)" style="margin:2px;">' +
		'<i class="fa"></i>'+
		'update</button>'+
		'<button class="btn btn-danger btn-sm" '+
			'ng-click="grid.appScope.deleteCallback(grid,row)" style="margin:2px;">'+
			'<i class="fa fa-trash"></i>'+
		'</button></div>';
		
		self.modalObj.gridOptions={
			rowHeight:40,
			columnDefs:[
			    {field:'name', name:'Screen Name'},       
			    {field:'wizard.name', name:'Wizard Name'},
			    {field: 'screen_wizard_sequence_id', name: 'Sequence id',
			          cellTemplate: '<select class="hover ui-grid-filter-input" name="copy" ' +
			          'ng-model="row.entity.screen_wizard_sequence_id"' +
			          'style="width: 100%; padding: 5px;"' +
			          'title="Copy noun properties from" autofocus="autofocus"' +
			          'ng-options="seqid.screen_wizard_sequence_id as seqid.screen_wizard_sequence_id for seqid in grid.appScope.wizards"' +
			          '<option value="">---Select---</option>' +
			          '</select></td>'
			    },  
				{name:'Action', cellTemplate: self.actionButtons},
			],
			//data:modalDataObj.wizard,
			data:$filter('orderBy')(modalDataObj.wizard, "screen_wizard_sequence_id", false),
			enableHorizontalScrollbar:0,
			minRowsToShow:8
		};
		
		self.modalObj.deleteCallback=modalDataObj.deleteCallback;
		self.modalObj.editCallback=modalDataObj.editCallback;
		
	};
	
	/**
	 * grid delete button action listener 
	 */
	self.deleteCallback=function(grid, row){
		self.deleteEntity=row.entity;
	}
	
	/**
	 * edit action has been triggered when user clicks!
	 */
	self.editCallback=function(grid,row){
		//self.close();
		//self.modalObj.editCallback(row.entity);
		self.modalObj.editCallback(row.entity,myCallbackFunction);
	}
	
	function myCallbackFunction(response) {
		//console.log("-->"+JSON.stringify(response));
		modalDataObj.wizard=response;
		self.init();
		}
	
	/**
	 * delete action has been triggered when user clicks on delete screen!
	 */
	self.deleteCall=function(){
		$log.log("triggering delete:",self.deleteEntity);
		self.close();
		self.modalObj.deleteCallback(modalDataObj.type, self.deleteEntity);
	};
	
	
	/**
	 * cancel handler for delete action
	 */
	self.cancelDelete=function(){
		$log.log("canceling delete!");
		self.deleteEntity=null;
	};
	
	self.init();
	
	
}])