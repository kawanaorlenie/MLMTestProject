$(document).ready(
		function() {
			//TODO this hostName must change when in production
			hostName="/MLMTestProject";

			$("#tabs").tabs(
					{
						activate : function(event, ui) {
							$.cookie("tabs_admin_selected", $("#tabs").tabs(
									"option", "active"));
						},
						active : $("#tabs").tabs({
							active : $.cookie("tabs_admin_selected")
						}),
					});

			$('#UserTableContainer').jtable({
				title : 'Table of users',
				actions : {
					listAction : hostName + '/user/list',
					createAction : hostName + '/user/create',
					updateAction : hostName + '/user/update',
					deleteAction : hostName + '/user/delete'
				},
				fields : {
					userId : {
						key : true,
						list : false,
					},
					userName : {
						title : 'User Name',
					},
					password : {
						title : 'Password',
					},
					email : {
						title : 'E-mail',
					},
					enabled : {
						title : 'Enabled',
						type : 'checkbox',
						values : {
							'false' : 'No',
							'true' : 'Yes'
						},
					},
					userRole : {
						title : 'User',
						type : 'checkbox',
						values : {
							'false' : 'No',
							'true' : 'Yes'
						},
					},
					adminRole : {
						title : 'Admin',
						type : 'checkbox',
						values : {
							'false' : 'No',
							'true' : 'Yes'
						},
					},
				}
			});

			$('#categoriesTableContainer').jtable(
					{
						title : 'Table of categories',
						actions : {
							listAction : document.location.href
									+ '/categories/loadRecords',
							createAction : document.location.href
									+ '/categories/create',
							updateAction : document.location.href
									+ '/categories/update',
							deleteAction : document.location.href
									+ '/categories/delete'
						},
						fields : {
							categoryId : {
								key : true,
								list : false,
							},
							categoryName : {
								title : 'Category Name',
							},
							categoryIconPath : {
								title : 'categoryIconPath',
							},

						}
					});

			$('#UserTableContainer').jtable('load');

			$('#categoriesTableContainer').jtable('load');
		});
