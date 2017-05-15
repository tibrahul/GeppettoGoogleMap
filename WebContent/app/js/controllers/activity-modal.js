/**
 * Since Geppetto v.90
 * Created: July 15, 2015
 * @author Pravin
 * 
 * Note: This controller will handle all the modal's related to the activity
 */

app.controller('ActivityModalCtrl',
		['$scope', '$log', '$modalInstance', 'type', 'modalDataObj','$http','RestURL',
		 function($scope, $log, $modalInstance, type, modalDataObj,$http,RestURL){
			
	var self=$scope;
	
	/**
	 * reference for external scope object for ui grid 
	 */
	self.scope=$scope;
	
	/**
	 * list of screens to create 
	 */
	self.screens=[];
	
	/**
	 * modal object which is used to bind the data
	 */
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
		action:'',
		activity:null
	};
	
	
	/**
	 * deletable entity
	 */
	self.deleteEntity=null;
	/**
	 * initializing activity modal controller
	 */
	self.init=function(){
		
		$log.debug('initializing activity modal controller! - ', type);
		$log.debug('modal data object:',angular.toJson(modalDataObj));
		
		self.screens=modalDataObj.screens;
		self.actionButtons='<div ><button class="btn btn-success btn-sm" ng-click="grid.appScope.editCallback(grid,row)" style="margin:2px;">' +
			'<i class="fa fa-edit"></i>'+
			'</button>'+
			'<button class="btn btn-danger btn-sm" '+
				'ng-click="grid.appScope.deleteCallback(grid,row)" style="margin:2px;">'+
				'<i class="fa fa-trash"></i>'+
			'</button></div>';
		
		self.modalObj.gridOptions={
			rowHeight:40,
			columnDefs:[
			    {field:'name', name:'Name'},
				{field:'label', name:'Label'},
				{field:'client_device_type_label', name:'Device Type'},
				{field:'description', name:'Description'},
				{field:'human_language_name', name:'language'},
				{name:'Action', cellTemplate: self.actionButtons}
			],
			data:modalDataObj.devices,
			enableHorizontalScrollbar:0,
			minRowsToShow:8
		};
		
		self.modalObj.title=modalDataObj.title;
		self.modalObj.type=modalDataObj.type;
		self.modalObj.screens=modalDataObj.screens;
		self.modalObj.editCallback=modalDataObj.editCallback;
		self.modalObj.deleteCallback=modalDataObj.deleteCallback;
		self.modalObj.action=modalDataObj.action;
		self.modalObj.activity=modalDataObj.activity;
	};

	/**
	 * clickhandler for delete and edit
	 *//*
	self.clickHandler={
		editCallback:function(obj){
			$log.debug("edit callback : ", obj);
			self.modalObj.editCallback(obj);
		},
		deleteCallback:function(obj){
			$log.debug("delete callback : ", obj);
			self.modalObj.deleteCallback(obj);
		}
	};*/
	
	self.editCallback=function(grid, row){
		self.close();
		//$log.debug("edit callback : ", grid, row);
		self.modalObj.editCallback(modalDataObj.type, row.entity);
	}
	
	/**
	 * grid delete button action listener 
	 */
	self.deleteCallback=function(grid, row){
		//self.close();
		//$log.debug("delete callback : ", grid, row);
		//self.modalObj.deleteCallback(modalDataObj.type, row.entity);
		self.deleteEntity=row.entity;
	}
	
	/**
	 * close handler for modal
	 */
	self.close=function(){
		  $modalInstance.dismiss('cancel');	
	};	
	
	/**
	 * create button handler for modal
	 */
	self.createButtonHandler=function(){
		  self.close();
		  modalDataObj.createScreenHandler(modalDataObj.type, self.modalObj.selectedDevice);
	};
	
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
	/**
	 * open create wizard modal
	 */
	self.createWizardModal=function(type){
		  //self.close();
		 modalDataObj.createWizardModal(type);
	};
	/**
	 * open edit wizard modal
	 */
	self.editWizardModal=function(type){
		  //self.close();
		 modalDataObj.editWizardModal(type);
	};
	
	self.init();
}])