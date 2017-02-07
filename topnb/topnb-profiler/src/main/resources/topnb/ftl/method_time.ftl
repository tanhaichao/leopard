                    <div class="span12">                    
                        <div class="head clearfix">
                            <div class="isw-grid"></div>
                            <h1>监控数据 >> ${typeName!}</h1>      
                            <ul class="buttons">
                            	<li>
                                    <a href="javascript:void(0)" class="isw-tab" style="width:132px; text-align:center" title="按监控入口查看统计数据">按入口</a>
                                    <ul class="dd-list" style="right: 0px;">
                                    	<li><a href="?entryName="><span class="isw-edit"></span> 全部</a></li>
                                    	<#if entryNameList??><#list entryNameList as entryName>
							            <li><a href="?entryName=${entryName!}"><span class="isw-edit"></span> ${entryName}</a></li>
							            </#list></#if>
                                    </ul>
                                </li>
                                
                              

                            </ul>       
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                	<tr>
                                        <th nowrap="nowrap" colspan="3" style="text-align: center;">总耗时</th>
                                        <th nowrap="nowrap" colspan="3" style="text-align: center;">慢请求</th>
                                        <th nowrap="nowrap" colspan="2" style="text-align: center;">比率</th>                                        
                                        <th nowrap="nowrap" colspan="2" style="text-align: center;">接口</th>
                                    </tr>
                                    <tr>
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=allCount')}">数量</a></th>
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=allTime')}">总耗时</a></th>
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=allAvgTime')}">平均</a></th>
                                    	
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=slowCount')}">数量</a></th>
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=slowTime')}">总耗时</a></th>
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=slowAvgTime')}">平均</a></th>

                                    	<th nowrap="nowrap" style="text-align: center;" alt="(当前方法耗时/该接口所有方法耗时)*100%">接口</th>
	                                    <th nowrap="nowrap" style="text-align: center;" alt="(当前方法耗时/所有Controller入口耗时)*100%">全部</th>                                        		
                                        
                                    	<th nowrap="nowrap" style="text-align: center;">分类</th>
                                    	<th nowrap="nowrap" style="text-align: center;">方法名称</th>
                                    </tr>
                                    
                                </thead>
                                
                                <tbody>
                                	<#if performanceVOList??>
                                	<#list performanceVOList as methodVO>
                                    <tr>
                                    	<td>${methodVO.allCount}</td>
	                                    <td>${time(methodVO.allTime)}</td>
	                                    <td>${avgTime(methodVO.allCount,methodVO.allTime)}</td>
	                                    
	                                    <td>${methodVO.slowCount}</td>
	                                    <td>${time(methodVO.slowTime)}</td>
	                                    <td>${avgTime(methodVO.slowCount,methodVO.slowTime)}</td>
                                        
                                        <td><a title="${methodVO.interfaceName}">${methodVO.interfaceRatio}%</a></td>
                                        <td>${methodVO.timeRatio}%</td>
                                        
                                        <td>${methodVO.categoryName!}</td>
                                        <td>${methodVO.simpleMethodName}</td>
                                    </tr>
                                    </#list>
                                    </#if>

                                </tbody>
                            </table>
                        </div>
                    </div>