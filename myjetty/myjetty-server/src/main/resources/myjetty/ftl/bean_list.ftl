
                    <div class="span12">                    
                        <div class="head clearfix">
                            <div class="isw-grid"></div>
                            <h1>Spring Bean列表</h1>
                            
                            <ul class="buttons">
                            	<li><a href="run.leo?category=${category!}" target="_blank" class="isw-tab" style="width:132px; text-align:center">运行所有</a></li>

                            </ul>      
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                    <tr>
                                    	<th nowrap="nowrap" style="width:26px">序号</th>
                                    	<th nowrap="nowrap" style="width:60px">方法数量</th>
                                    	<th nowrap="nowrap" style="width:83px">Bean名称</th>
                                    	<th nowrap="nowrap" style="">类名称</th>
                                    	<th nowrap="nowrap" style="width:100px">运行</th>
                                    </tr>
                                    
                                </thead>
                                <tbody>
                                	<#if beanList??>
                                	<#list beanList as bean>
                                	<tr>
                                		<td>${bean_index+1}</td>
                                		<td>${bean.methodCount}</td>
                                		<td>${bean.name}</td>
                                		<td>${bean.className}</td>
                                		<td><a href="run.leo?beanName=${bean.name}" target="_blank">运行</a></td>
                                	</tr>
                                	</#list>
                                    </#if>
                                	
                                </tbody>
                            </table>
                        </div>
                    </div>  