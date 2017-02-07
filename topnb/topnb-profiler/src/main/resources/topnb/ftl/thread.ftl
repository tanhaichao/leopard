
                    <div class="span12">                    
                        <div class="head clearfix">
                            <div class="isw-grid"></div>
                            <h1>线程数量</h1>
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                    <tr>
                                    	<th nowrap="nowrap" style="width:26px">序号</th>
                                    	<th nowrap="nowrap" style="width:63px">分类</th>
                                    	<th nowrap="nowrap" style="width:30px">数量</th>
                                    	<th nowrap="nowrap" style="">线程名称</th>
                                    	<th nowrap="nowrap" style="width:100px">备注</th>
                                    </tr>
                                    
                                </thead>
                                <tbody>
                                	<#if threadInfoList??>
                                	<#list threadInfoList as threadInfo>
                                	<tr>
                                		<td>${threadInfo_index+1}</td>
                                		<td>${threadInfo.category.name}</td>
                                		<td>${threadInfo.currentSize}</td>
                                		<td>${threadInfo.threadName}</td>
                                		<td>${threadInfo.content!}</td>
                                	</tr>
                                	</#list>
                                    </#if>
                                	
                                </tbody>
                            </table>
                        </div>
                    </div>  