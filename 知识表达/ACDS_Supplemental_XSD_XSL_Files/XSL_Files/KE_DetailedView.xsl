<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
<!--<xsl:import href="ui.xsl"/>-->
	<xsl:output method="html" indent="yes"/>
	<!--
	CREATED: 4/19/2011 Y. Vulfovich yvulfovich@partners.org
-->
	<xsl:variable name="lcletters">abcdefghijklmnopqrstuvwxyz</xsl:variable>
	<xsl:variable name="ucletters">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable>
	
	<xsl:template match="StructuredCDSKnowledge|StructuredGuideline|structuredCDSKnowledge|structuredGuideline">
		<style>
			.divider {height:2px;background-color:blue;width:100%;}
			.heading {font-weight:bold;font-size:13px;font-family:Arial;align:center;}
			td.heading {background-color: #F2F3F4; margin-bottom: 2px;
			  font-weight:bold;font-size:13px;font-family:Arial;}
			.content {font-size:12;font-family:Arial;align:right;font-weight:normal;}
			.headingContent {font-size:12;font-family:Arial;align:right;font-weight:normal;}
			td.headingContent{background-color: #F2F3F4; margin-bottom: 2px;
			  font-weight:normal;font-size:12px;font-family:Arial;}
			.field {font-size:12;font-family:Arial;font-weight:normal;}
			
			.fieldHeader {font-size:12;font-family:Arial;font-weight:bold;}
			.checkMark {font-size:13;font-family:Webdings;font-weight:bold;}
			.headerTitle {font-size:14;font-family:Arial;font-weight:bold;}
			.fieldLink {font-size:12;font-family:Arial;color:blue;cursor:hand;text-decoration:underline;}
			.fieldTitle {font-size:13px;font-family:Arial;}
			#page,table {background: white}
			
		</style>
		
		<p align="center" class=".headerTitle">Knowledge Engineer Style Sheet 
		</p>
		<table width="100%">
			<tr>
				<td class="divider"/>
			</tr>
			<tr>
				<td class="heading">
					<xsl:value-of select="Metadata/identity/description"/>
					<br/>
				</td>
			</tr>
			<tr>
				<td class="divider"/>
			</tr>
		</table>
		
		<xsl:if test="Metadata/contributor/organization/name!='' or Metadata/implementation/knowledgeType!=''">
			<table width="100%">
				<xsl:if test="Metadata/contributor/organization/name!=''">
					<tr>
						<td width="12%" class="fieldTitle" valign="top"><b>Contributor:</b></td>
						<td width="88%" class="field" valign="top"><xsl:value-of select="Metadata/contributor/organization/name" /></td>
					</tr>
				</xsl:if>
				<xsl:if test="Metadata/implementation/knowledgeType">
				<tr>
					<td width="12%" class="fieldTitle" valign="top"><b>Knowledge Type:</b></td>
					<td width="88%" class="field" valign="top"><xsl:value-of select="Metadata/implementation/knowledgeType" /></td>
				</tr>
				</xsl:if>
				<xsl:if test="Metadata/stateChangeEvent/eventCode">
					<tr>
						<td width="12%" class="fieldTitle" valign="top"><b>Change Event:</b></td>
						<td width="88%" class="field" valign="top">
							<xsl:value-of select="Metadata/stateChangeEvent/eventCode" />,
							<xsl:value-of select="Metadata/stateChangeEvent/eventDateTime" />
						</td>
					</tr>
				</xsl:if>
			</table>
			<br />
		</xsl:if>
		
		<xsl:if test="Metadata/identity">
			<xsl:apply-templates select="Metadata/identity" />
			<br />
		</xsl:if>
			
		<xsl:if test="Metadata/coverage">
			<span class="heading">
				<!--Search Criteria-->
				<xsl:if test="Metadata/identity/title!=''">
					<xsl:value-of select="Metadata/identity/title"/>
				</xsl:if>
			</span>
			<table width="100%" cellspacing="2">
				<xsl:for-each select="Metadata/coverage">
					<tr>
						<td width="11%" class="field" valign="top">
							<xsl:value-of select="focus"/>:&#160;
						</td>
						<td class="field" width="89%" valign="top">
							<xsl:value-of select="code/@displayName"/>&#160;(<xsl:value-of select="code/@codeSystemName"/>:&#160;&#160;<xsl:value-of select="code/@code"/>)
						</td>
					</tr>
				</xsl:for-each>
			</table>
			<br/>
		</xsl:if>
		
		<xsl:if test="Definition">
			<xsl:apply-templates select="Definition"/>
			<br/>
		</xsl:if>
		<xsl:if test="ApplicableScenario">
			<xsl:for-each select="ApplicableScenario">
				<xsl:choose>
					<xsl:when test="LogicalConditionSet">
						<xsl:apply-templates select="LogicalConditionSet" />
					</xsl:when>
					<xsl:when test="LogicalConditionSingle">
						<xsl:apply-templates select="LogicalConditionSingle"/>
					</xsl:when>
				</xsl:choose>
			</xsl:for-each>
			<br/>
		</xsl:if>
		
		<xsl:if test="module">
			<xsl:for-each select="module">
				<xsl:if test="module/Metadata/identity/title != ''">
					<table width="100%">
						<tr>
							<td class="divider"/>
						</tr>
						<tr>
							<td class="heading">
								<xsl:value-of select="Metadata/identity/title"/>
								<br/>
								<span class="headingContent">
									<xsl:value-of select="Metadata/identity/description"/>
									<!-- new code for relatedResource -->
									<xsl:if test="Metadata/identity">
										<xsl:apply-templates select="Metadata/identity" />
									</xsl:if>
									<br/>
								</span>
							</td>
						</tr>
						<tr>
							<td class="divider"/>
						</tr>
					</table>
				</xsl:if>
				<xsl:if test="recommendation">
					<xsl:for-each select="recommendation">
						<xsl:if test="Metadata/identity/title!=''">
							<span class="fieldTitle">
								<b><xsl:value-of select="Metadata/identity/title"/></b>
							</span>
							<br/>
						</xsl:if>
						<xsl:if test="Metadata/identity/description!=''">
							<span class="field">
								<xsl:value-of select="Metadata/identity/description"/>
							</span>
							<!-- new code for relatedResource -->
							<xsl:if test="Metadata/identity">
								<xsl:apply-templates select="Metadata/identity" />
							</xsl:if>
							<br/>
						</xsl:if>
						<xsl:if test="ApplicableScenario">
							<xsl:apply-templates select="ApplicableScenario" />
						</xsl:if>
						<xsl:apply-templates select="Advice"/>
						<xsl:if test="position() != last()">
							<br/>
							<table width="50%">
								<tr>
									<td class="divider"/>
								</tr>
							</table>
						</xsl:if>
					</xsl:for-each>
					<br/>
				</xsl:if>
			</xsl:for-each>
			<br/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="Metadata/identity">
		<xsl:if test="relatedResource">
			<span class="heading">Related Resources:</span>
			<table width="100%" cellspacing="2">
				<xsl:for-each select="relatedResource">
					<xsl:call-template name="relatedResource" />
					<tr>
						<td colspan="2" height="2px" class="field"></td>
					</tr>
				</xsl:for-each>
			</table>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="relatedResource">
		<tr>
			<td width="11%" class="field" valign="top">
				<xsl:if test="type!=''"><xsl:value-of select="type" />:&#160;</xsl:if>
			</td>
			<td width="89%" class="field" valign="top">
				<xsl:choose>
					<xsl:when test="resource/description!=''">
						<xsl:value-of select="resource/description" />
					</xsl:when>
					<xsl:otherwise><xsl:value-of  select="resource/title" /></xsl:otherwise>
				</xsl:choose>
				<xsl:if test="resource/location!=''">:&#160;<span class="fieldLink"><a target='_blank'><xsl:attribute name="href"><xsl:value-of select="resource/location" /></xsl:attribute><xsl:value-of  select="resource/location" /></a></span></xsl:if>
			</td>
		</tr>
	</xsl:template>
	

	<xsl:template match="Advice">
		<xsl:if test="title!=''">
			<span class="fieldTitle"><b><xsl:value-of select="title" /></b></span>
	  </xsl:if>
	  
		<xsl:apply-templates select="ActionConcrete"/>
	</xsl:template>
	<xsl:template match="ActionConcrete">
		<xsl:apply-templates select="ActionOrganizer"/>
	</xsl:template>
	<xsl:template match="ActionOrganizer">
		<xsl:param name="operatorCode" select="operator/@code"/>
		<xsl:param name="testKnowledge" select="ancestor::*/ancestor::*/ancestor::*/ancestor::*/ancestor::*/ancestor::*/Metadata/implementation/knowledgeType" />
		<table width="100%" cellspacing="0">
			<xsl:if test="ActionConcrete/ObservationRequest and ancestor::*/ancestor::*/Metadata/implementation/knowledgeType='Documentation Template/Form'">
				<tr>
					<td width="12%" valign="top" class="fieldHeader">
						Observation Request:&#160;
					</td>
					<td width="8%" class="fieldHeader" valign="top"><i>Check if Yes</i></td>
					<td width="65%" class="fieldHeader" valign="top"><i>Observation</i></td>
					<td width="15%" class="fieldHeader" valign="top"><i>Observation Value</i></td>
				</tr>
			</xsl:if>
			<xsl:for-each select="ActionConcrete">
				<xsl:sort select="EncounterRequest or EventRequest or eventRequest or KnowledgeAssetRequest or MessageRequest or ProcedureRequest or SubstanceAdministrationRequest or SupplyRequest"  />
				<xsl:choose>
					<xsl:when test="ActionOrganizer">
						<xsl:apply-templates select="ActionOrganizer"/>
					</xsl:when>
					<xsl:when test="MessageRequest">
						<tr>
							<td colspan="4" class="field">
								<xsl:apply-templates select="MessageRequest"/>
							</td>
						</tr>
					</xsl:when>
					<xsl:when test="ObservationRequest">
						<xsl:choose>
							<xsl:when test="$testKnowledge='Documentation Template/Form'">
								<xsl:apply-templates select="ObservationRequest" />
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<td colspan="4"  class="field">
										<xsl:apply-templates select="ObservationRequest" />
										<span class="field">
											<xsl:if test="position() != last() and $testKnowledge!='Documentation Template/Form'">&#160;&#160;
												<xsl:choose>
													<xsl:when test="$operatorCode='XOR'"><b>OR</b></xsl:when>
													<xsl:when test="$operatorCode='AND'"><b>AND</b></xsl:when>
													<xsl:otherwise>&#160;
														<xsl:if test="$operatorCode='OR'">
															<xsl:if test="ancestor::*/ancestor::*/ancestor::*/ancestor::*/ActionOrganizer">
																&#160;&#160;<b>OR</b>
															</xsl:if>
														</xsl:if>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:if>
										</span>
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<tr>
							<td colspan="4" class="field">
								<xsl:apply-templates select="ClinicalStateRequest"/>
								<xsl:apply-templates select="EncounterRequest"/>
								<xsl:apply-templates select="EventRequest|eventRequest"/>
								<xsl:apply-templates select="KnowledgeAssetRequest"/>
								<xsl:apply-templates select="ProcedureRequest"/>
								<xsl:apply-templates select="SubstanceAdministrationRequest"/>
								<xsl:apply-templates select="SupplyRequest"/>
								<xsl:if test="not(ObservationRequest) and not(MessageRequest)">
									<span class="field">
										<xsl:if test="position() != last()">&#160;&#160;
											<xsl:choose>
												<xsl:when test="$operatorCode='XOR'"><b>OR</b></xsl:when>
												<xsl:when test="$operatorCode='AND'"><b>AND</b></xsl:when>
												<xsl:otherwise>&#160;
													<xsl:if test="$operatorCode='OR'">
														<xsl:if test="ancestor::*/ancestor::*/ancestor::*/ancestor::*/ActionOrganizer">
															&#160;&#160;<b>OR</b>
														</xsl:if>
													</xsl:if>
												</xsl:otherwise>
											</xsl:choose>
										</xsl:if>
									</span>
								</xsl:if>
								<br/>
							</td>
						</tr>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</table>
	</xsl:template>
	<xsl:template match="EncounterRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Encounter Request'" />
			<xsl:with-param name="hoverText" select="encounterProvider/@displayName"/>
			<xsl:with-param name="hoverCode" select="encounterProvider/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="encounterProvider/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="KnowledgeAssetRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Knowledge Asset Request'" />
			<xsl:with-param name="hoverText" select="resource/knowledgeResourceType/@displayName"/>
			<xsl:with-param name="hoverCode" select="resource/knowledgeResourceType/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="resource/knowledgeResourceType/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="resource/title" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="resource/location" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="MessageRequest">
		<xsl:param name="requestName" select="'Message'" />
		<xsl:param name="hoverText" select="target/@displayName"/>
		<xsl:param name="hoverCode" select="target/@code"/>
		<xsl:param name="hoverCodeSystemName" select="target/@codeSystemName"/>
		<xsl:param name="ancestorCode" select="ancestor::*/operator/@code" />
		<tr>
			<td colspan="4" class="field">
				<table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="field">
							<b>
								<xsl:choose>
									<xsl:when test="$hoverCode='309343006' or $hoverCode='Physician'">Physician Message:</xsl:when>
									<xsl:when test="$hoverCode='116154003'">Patient Message:</xsl:when>
									<xsl:when test="$hoverCode='125677006' or $hoverCode='Parent'">Relative Message:</xsl:when>
									<xsl:otherwise>Display Message:</xsl:otherwise>
								</xsl:choose>
								&#160;(<xsl:value-of select="$hoverCodeSystemName"/>:&#160;<xsl:value-of select="$hoverCode"/>):&#160;
							</b>
						</td>
					</tr>
					<tr>
						<td class="field">
							<xsl:choose>
								<xsl:when test="Message/freeTextMessage/@value">
									<xsl:value-of select="Message/freeTextMessage/@value"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="Message/freeTextMessage"/>
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
						
				</table>
			</td>
		</tr>
	</xsl:template>
	
	<xsl:template match="MessageRequest10122011">
		<xsl:param name="requestName" select="'Message'" />
		<xsl:param name="hoverText" select="target/@displayName"/>
		<xsl:param name="hoverCode" select="target/@code"/>
		<xsl:param name="hoverCodeSystemName" select="target/@codeSystemName"/>
		<xsl:param name="ancestorCode" select="ancestor::*/operator/@code" />
		<tr>
			<td colspan="4" class="field">
				<table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="field">
							<b>
								<xsl:choose>
									<xsl:when test="$hoverCode='309343006' or $hoverCode='Physician'">Physician Message:</xsl:when>
									<xsl:when test="$hoverCode='116154003'">Patient Message:</xsl:when>
									<xsl:when test="$hoverCode='125677006' or $hoverCode='Parent'">Relative Message:</xsl:when>
									<xsl:otherwise>Display Message:</xsl:otherwise>
								</xsl:choose>
								&#160;(<xsl:value-of select="$hoverCodeSystemName"/>:&#160;<xsl:value-of select="$hoverCode"/>):&#160;
							</b>
						</td>
					</tr>
					<xsl:choose>
						<xsl:when test="messageParameter">
							<tr>
								<td class="field">
									<xsl:for-each select="messageParameter">
										<table class="field" width="100%">
											<tr>
												<td class="field" width="15%" valign="top">
													<xsl:value-of select="name" />
												</td>
												<td class="field" width="85%" valign="top">
													<xsl:choose>
														<xsl:when test="value/@displayName">
															<xsl:value-of select="value/@displayName" />&#160;(code: <xsl:value-of select="value/@code" />)
														</xsl:when>
														<xsl:otherwise>
															<xsl:value-of select="value" />
														</xsl:otherwise>
													</xsl:choose>
												</td>
											</tr>
										</table>
									</xsl:for-each>
								</td>
							</tr>
						</xsl:when>
						<xsl:otherwise>
							<tr>
								<td class="field">
									<xsl:choose>
										<xsl:when test="Message/freeTextMessage/@value">
											<xsl:value-of select="Message/freeTextMessage/@value"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="Message/freeTextMessage"/>
										</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</xsl:otherwise>
					</xsl:choose>
				</table>
			</td>
		</tr>
	</xsl:template>
	
	<xsl:template match="MessageRequestNew">
		<xsl:param name="requestName" select="'Message'" />
		<xsl:param name="hoverText" select="target/@displayName"/>
		<xsl:param name="hoverCode" select="target/@code"/>
		<xsl:param name="hoverCodeSystemName" select="target/@codeSystemName"/>
		<xsl:param name="ancestorCode" select="ancestor::*/operator/@code" />
		<tr>
			<td colspan="4" class="field">
				<b>
					<xsl:choose>
						<xsl:when test="$hoverCode='309343006' or $hoverCode='Physician'">Physician Message:</xsl:when>
						<xsl:when test="$hoverCode='116154003'">Patient Message:</xsl:when>
						<xsl:when test="$hoverCode='125677006' or $hoverCode='Parent'">Relative Message:</xsl:when>
						<xsl:otherwise>Display Message:</xsl:otherwise>
					</xsl:choose>
					&#160;(<xsl:value-of select="$hoverCodeSystemName"/>:&#160;<xsl:value-of select="$hoverCode"/>):&#160;
				</b><br />
				<xsl:value-of select="Message/freeTextMessage/@value"/>
			</td>
		</tr>
	</xsl:template>
	
	<xsl:template match="ObservationRequest">
		<xsl:param name="requestName" select="'Observation Request'" />
		<xsl:param name="hoverText" select="observationCode/@displayName"/>
		<xsl:param name="hoverCode" select="observationCode/@code"/>
		<xsl:param name="hoverCodeSystemName" select="observationCode/@codeSystemName"/>
		<xsl:param name="ancestorCode" select="ancestor::*/operator/@code" />
		<xsl:param name="selectionCriteria" select="selectionCriteria" />
		<xsl:param name="title" select="''" />
		<xsl:param name="textAlternative" select="textAlternative" />
		<xsl:param name="location" select="''" />
		<xsl:param name="targetDisplayName" select="target/@displayName" />
		<xsl:param name="targetCode" select="target/@code" />
		<xsl:param name="targetCodeSystemName" select="target/@codeSystemName" />
		<xsl:param name="testKnowledge" select="ancestor::*/ancestor::*/Metadata/implementation/knowledgeType" />
		<xsl:choose>
			<xsl:when test="$testKnowledge='Documentation Template/Form'">
				<tr>
					<td width="15%" class="field">&#160;</td>
					<td class="field" valign="top">
						<xsl:element name="input">
							<xsl:attribute name="type">checkbox</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="$hoverText"/></xsl:attribute>
							<xsl:if test="$selectionCriteria='recommended'">
								<xsl:attribute name="checked">checked</xsl:attribute>
							</xsl:if>
						</xsl:element>
					</td>
					<td class="field">
						<xsl:choose>
							<xsl:when test="textAlternative!=''">
								<xsl:value-of select="textAlternative"/>&#160;
								<xsl:if test="$hoverText!=''">-&#160;<xsl:value-of select="$hoverText" />&#160;</xsl:if>
							</xsl:when>
							<xsl:otherwise><xsl:value-of select="$hoverText"/></xsl:otherwise>
						</xsl:choose>
						&#160;
						(<xsl:value-of select="$hoverCodeSystemName"/>:&#160;&#160;<xsl:value-of select="$hoverCode"/>)
						<xsl:if test="$targetDisplayName!=''">
							&#160;&#160;<i><b>Target:</b></i>&#160;<xsl:value-of select="$targetDisplayName" />&#160;(<xsl:value-of select="$targetCodeSystemName" />:&#160;&#160;<xsl:value-of select="$targetCode" />)
						</xsl:if>
						<xsl:if test="$location!=''">&#160;&#160;&#160;&#160;&#160;&#160;&#160;<span class="fieldLink">
							<a target='_blank'><xsl:attribute name="href"><xsl:value-of select="$location" /></xsl:attribute><xsl:value-of select="$location" /></a>
						</span></xsl:if>&#160;
					</td>
					<td class="field" valign="top">
						<xsl:value-of select="observationValue" />&#160;
					</td>
				</tr>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="createRequest">
					<xsl:with-param name="requestName" select="'Observation Request'" />
					<xsl:with-param name="hoverText" select="observationCode/@displayName"/>
					<xsl:with-param name="hoverCode" select="observationCode/@code"/>
					<xsl:with-param name="hoverCodeSystemName" select="observationCode/@codeSystemName"/>
					<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
					<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
					<xsl:with-param name="title" select="''" />
					<xsl:with-param name="textAlternative" select="textAlternative" />
					<xsl:with-param name="location" select="''" />
					<xsl:with-param name="targetDisplayName" select="target/@displayName" />
					<xsl:with-param name="targetCode" select="target/@code" />
					<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="ProcedureRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Procedure Request'" />
			<xsl:with-param name="hoverText" select="procedureTypeCode/@displayName"/>
			<xsl:with-param name="hoverCode" select="procedureTypeCode/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="procedureTypeCode/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="SubstanceAdministrationRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Substance Administration Request'" />
			<xsl:with-param name="hoverText" select="codedProductName/@displayName"/>
			<xsl:with-param name="hoverCode" select="codedProductName/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="codedProductName/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="EventRequest|eventRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Event Request'" />
			<xsl:with-param name="hoverText" select="eventType/@displayName"/>
			<xsl:with-param name="hoverCode" select="eventType/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="eventType/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="SupplyRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Supply Request'" />
			<xsl:with-param name="hoverText" select="supplyTypeCode/@displayName"/>
			<xsl:with-param name="hoverCode" select="supplyTypeCode/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="supplyTypeCode/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="ClinicalStateRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Clinical State Request'" />
			<xsl:with-param name="hoverText" select="stateCode/@displayName"/>
			<xsl:with-param name="hoverCode" select="stateCode/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="stateCode/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
		
	<xsl:template match="LogicalConditionSet">
		<xsl:param name="logicalConditionSetCode" select="operator/@code" />
		<xsl:if test="title!=''">
			<span class="fieldTitle">
				<b><xsl:value-of select="title"/></b>
			</span>
			<br />
		</xsl:if>
		<xsl:choose>
			<xsl:when test="$logicalConditionSetCode!='' and (ApplicableScenario)">
			<table width="100%" border="1">
				<tr>
					<td width="2%" class="field"><b><i><xsl:value-of select="$logicalConditionSetCode" /></i></b></td>
					<td width="98%" class="field">
						
						<xsl:if test="ApplicableScenario">
							<xsl:apply-templates select="ApplicableScenario" />
						</xsl:if>
					</td>
				</tr>
			</table>	
			</xsl:when>
			<xsl:when test="ApplicableScenario">
				<!-- NEW CODE: ADDED TABLE 11/07/2011 -->
				<table width="100%" border="1">
				<tr>
					<td class="field">
				<xsl:if test="ApplicableScenario">
					<xsl:apply-templates select="ApplicableScenario" />
				</xsl:if>
				</td>
				</tr>
				</table>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="LogicalConditionSingle">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td colspan="3" class="field">
					<xsl:choose>
						<xsl:when test="description!=''">
							<b><xsl:value-of select="description"/></b>
						</xsl:when>
						<xsl:when test="title!='' and ancestor::*/ancestor::*/recommendation!=''">
							<b>
								<xsl:value-of select="title"/>
							</b>
						</xsl:when>
						<xsl:otherwise>
							<b><xsl:value-of select="title"/></b>
						</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
			<xsl:if test="expression!='' or dataMapping/expression!=''">
				<tr>
					<td width="2%" class="field">&#160;</td>
					<td width="2%" class="field">&#160;</td>
					<td width="15%" class="field" valign="top">Expression:</td>
					<td width="81%" valign="top" class="field" align="left">
						<xsl:if test="expression!=''"><xsl:value-of select="expression"/></xsl:if>
						<xsl:if test="expression!='' and dataMapping/expression!=''"><br /></xsl:if>
						<xsl:if test="dataMapping/expression!=''"><xsl:value-of select="normalize-space(dataMapping/expression)"/></xsl:if>
					</td>
				</tr>
			</xsl:if>
			<xsl:if test="dataMapping!=''">
				<xsl:apply-templates select="dataMapping/Data">
					<xsl:sort select="ClinicalState or gender or Immunization or LaboratoryResult or Medication or Procedure or socialHistory or VitalSign" />
				</xsl:apply-templates>
			</xsl:if>
		</table>
	</xsl:template>
	
	<xsl:template match="dataMapping/Data">
		<xsl:if test="ClinicalState">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td width="2%" class="field">&#160;</td>
				<td width="15%" valign="top" class="field">Clinical State:</td>
				<td width="81%" valign="top" class="field">
					<table cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
						<xsl:call-template name="createColumns" />					
						<xsl:for-each select="ClinicalState">
							<xsl:call-template name="createTypeStatusTable">
								<xsl:with-param  name="displayNameType" select="stateCode/@displayName" />
								<xsl:with-param  name="codeSystemNameType" select="stateCode/@codeSystemName" />
								<xsl:with-param  name="codeType" select="stateCode/@code" />
								<xsl:with-param  name="displayNameStatus" select="stateStatus/@displayName" />
								<xsl:with-param  name="codeSystemNameStatus" select="stateStatus/@codeSystemName" />
								<xsl:with-param  name="codeStatus" select="stateStatus/@code" />
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="5px"  class="field"/>
			</tr>
		</xsl:if>
		<xsl:if test="gender">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td width="2%" class="field">&#160;</td>
				<td class="field" valign="top" width="15%">Gender:</td>
				<td valign="top" class="field" width="81%">
					<table cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
						<xsl:call-template name="createColumns" />
						<xsl:for-each select="gender">
							<xsl:call-template name="createTypeStatusTable">
								<xsl:with-param  name="displayNameType" select="@displayName" />
								<xsl:with-param  name="codeSystemNameType" select="@codeSystemName" />
								<xsl:with-param  name="codeType" select="@code" />
								<xsl:with-param  name="displayNameStatus" select="''" />
								<xsl:with-param  name="codeSystemNameStatus" select="''" />
								<xsl:with-param  name="codeStatus" select="''" />
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="5px" class="field" />
			</tr>
		</xsl:if>
		<xsl:if test="Immunization">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td width="2%" class="field">&#160;</td>
				<td class="field" valign="top" width="15%">Immunization:</td>
				<td valign="top" class="field" width="81%">
					<table cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
						<xsl:call-template name="createColumns" />
						<xsl:for-each select="Immunization">
							<xsl:call-template name="createTypeStatusTable">
								<xsl:with-param  name="displayNameType" select="codedProductClass/@displayName" />
								<xsl:with-param  name="codeSystemNameType" select="codedProductClass/@codeSystemName" />
								<xsl:with-param  name="codeType" select="codedProductClass/@code" />
								<xsl:with-param  name="displayNameStatus" select="administrationStatus/@displayName" />
								<xsl:with-param  name="codeSystemNameStatus" select="administrationStatus/@codeSystemName" />
								<xsl:with-param  name="codeStatus" select="administrationStatus/@code" />
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="5px" class="field" />
			</tr>
		</xsl:if>
		<xsl:if test="LaboratoryResult">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td width="2%" class="field">&#160;</td>
				<td class="field" valign="top" width="15%">Laboratory Result:</td>
				<td valign="top" class="field" width="81%">
					<table cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
						<xsl:call-template name="createColumnsForLabs" />
						<xsl:for-each select="LaboratoryResult">
						<tr>
								<td class="field" valign="top">
									<xsl:value-of select="resultType/@displayName"/>&#160;&#160;(<xsl:value-of select="resultType/@codeSystemName"/>:&#160;&#160;<xsl:value-of select="resultType/@code"/>)
								</td>
								<td class="field" valign="top">
									<xsl:if test="resultStatus/@codeSystemName!=''">
										<xsl:value-of select="resultStatus/@displayName"/>&#160;&#160;(<xsl:value-of select="resultStatus/@codeSystemName"/>:&#160;&#160;
										<xsl:value-of select="resultStatus/@code"/>)
									</xsl:if>&#160;
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="5px" class="field" />
			</tr>
		</xsl:if>
		<xsl:if test="Medication">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td width="2%" class="field">&#160;</td>
				<td class="field" valign="top" width="15%">Medication:</td>
				<td valign="top" class="field" width="81%">
					<table cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
						<xsl:call-template name="createColumnsForMeds" />
						<xsl:for-each select="Medication">
							<tr>
								<td class="field" valign="top">
									<xsl:if test="codedProductClass/@codeSystemName!=''">
										<xsl:value-of select="codedProductClass/@displayName"/>&#160;&#160;(<xsl:value-of select="codedProductClass/@codeSystemName"/>:&#160;&#160;
										<xsl:value-of select="codedProductClass/@code"/>)
									</xsl:if>
								</td>
								<td class="field" valign="top">
									<xsl:if test="statusOfMedication/@codeSystemName!=''">
										<xsl:value-of select="statusOfMedication/@displayName"/>&#160;&#160;(<xsl:value-of select="statusOfMedication/@codeSystemName"/>:&#160;&#160;
										<xsl:value-of select="statusOfMedication/@code"/>)
									</xsl:if>
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="5px" class="field" />
			</tr>
		</xsl:if>
		<xsl:if test="Procedure">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td width="2%" class="field">&#160;</td>
				<td class="field" valign="top" width="15%">Procedure:</td>
				<td valign="top" class="field" width="81%">
					<table cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
						<xsl:call-template name="createColumns" />
						<xsl:for-each select="Procedure">
							<xsl:call-template name="createTypeStatusTable">
								<xsl:with-param  name="displayNameType" select="procedureType/@displayName" />
								<xsl:with-param  name="codeSystemNameType" select="procedureType/@codeSystemName" />
								<xsl:with-param  name="codeType" select="procedureType/@code" />
								<xsl:with-param  name="displayNameStatus" select="procedureStatus/@displayName" />
								<xsl:with-param  name="codeSystemNameStatus" select="procedureStatus/@codeSystemName" />
								<xsl:with-param  name="codeStatus" select="procedureStatus/@code" />
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="5px" class="field" />
			</tr>
		</xsl:if>
		<xsl:if test="socialHistory">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td width="2%" class="field">&#160;</td>
				<td class="field" valign="top" width="15%">Social History:</td>
				<td valign="top" class="field" width="81%">
					<table cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
						<xsl:call-template name="createColumns" />
						<xsl:for-each select="socialHistory">
							<xsl:call-template name="createTypeStatusTable">
								<xsl:with-param  name="displayNameType" select="socialHistoryObservedValue/@displayName" />
								<xsl:with-param  name="codeSystemNameType" select="socialHistoryObservedValue/@codeSystemName" />
								<xsl:with-param  name="codeType" select="socialHistoryObservedValue/@code" />
								<xsl:with-param  name="displayNameStatus" select="socialHistoryStatus/@displayName" />
								<xsl:with-param  name="codeSystemNameStatus" select="socialHistoryStatus/@codeSystemName" />
								<xsl:with-param  name="codeStatus" select="socialHistoryStatus/@code" />
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="5px" class="field" />
			</tr>
		</xsl:if>
		<xsl:if test="VitalSign">
			<tr>
				<td width="2%" class="field">&#160;</td>
				<td width="2%" class="field">&#160;</td>
				<td class="field" valign="top" width="15%">Vital Sign:</td>
				<td valign="top" class="field" width="81%">
					<table cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
						<xsl:call-template name="createColumns" />
						<xsl:for-each select="VitalSign">
							<xsl:call-template name="createTypeStatusTable">
								<xsl:with-param  name="displayNameType" select="vsType/@displayName" />
								<xsl:with-param  name="codeSystemNameType" select="vsType/@codeSystemName" />
								<xsl:with-param  name="codeType" select="vsType/@code" />
								<xsl:with-param  name="displayNameStatus" select="vsStatus/@displayName" />
								<xsl:with-param  name="codeSystemNameStatus" select="vsStatus/@codeSystemName" />
								<xsl:with-param  name="codeStatus" select="vsStatus/@code" />
							</xsl:call-template>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="5px" class="field" />
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template match="Definition">
		<table width="100%" cellpadding="2" cellspacing="2" cols="2">
			<tr>
				<td class="field">
					<b>Display Name</b>
				</td>
				<td class="field">
					<b>Code</b>
				</td>
			</tr>
			<xsl:for-each select="definition">
				<tr>
					<td class="field" valign="top">
						<xsl:value-of select="code/@displayName"/>
					</td>
					<td class="field">
						<xsl:value-of select="code/@code"/>&#160;(<xsl:value-of select="code/@codeSystemName"/>:&#160;<xsl:value-of select="code/@codeSystemVersion"/>)
			</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	<xsl:template name="createRequest">
		<xsl:param name="requestName" />
		<xsl:param name="hoverText"/>
		<xsl:param name="hoverCode"/>
		<xsl:param name="hoverCodeSystemName"/>
		<xsl:param name="ancestorCode" />
		<xsl:param name="selectionCriteria" />
		<xsl:param name="title" />
		<xsl:param name="textAlternative" />
		<xsl:param name="location" />
		<xsl:param name="targetDisplayName"  />
		<xsl:param name="targetCode" />
		<xsl:param name="targetCodeSystemName" />
		<xsl:choose>
			<xsl:when test="$selectionCriteria='false'" />
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$ancestorCode='XOR'">&#160;&#160;&#160;
						<xsl:element name="input">
							<xsl:attribute name="type">radio</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="$hoverText"/></xsl:attribute>
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="input">
							<xsl:attribute name="type">checkbox</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="$hoverText"/></xsl:attribute>
							<xsl:if test="$selectionCriteria='recommended'">
								<xsl:attribute name="checked">checked</xsl:attribute>
							</xsl:if>
						</xsl:element>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
		<span class="field" title="{$hoverText}">
			<b><xsl:value-of select="$requestName" />:&#160;&#160;</b>
			<xsl:choose>
				<xsl:when test="$textAlternative!=''">
					<xsl:value-of select="$textAlternative"/>&#160;
					<xsl:if test="$hoverText!=''">-&#160;<xsl:value-of select="$hoverText" />&#160;</xsl:if>
				</xsl:when>
				<xsl:when test="$title!=''" >
					<xsl:value-of select="$title"/>&#160;
					<xsl:if test="$hoverText!=''">-&#160;<xsl:value-of select="$hoverText" />&#160;</xsl:if>
				</xsl:when>
				<xsl:otherwise><xsl:value-of select="$hoverText"/></xsl:otherwise>
			</xsl:choose>
			&#160;
			(<xsl:value-of select="$hoverCodeSystemName"/>:&#160;&#160;<xsl:value-of select="$hoverCode"/>)
			<xsl:if test="$targetDisplayName!=''">
				&#160;&#160;<i><b>Target:</b></i>&#160;<xsl:value-of select="$targetDisplayName" />&#160;(<xsl:value-of select="$targetCodeSystemName" />:&#160;&#160;<xsl:value-of select="$targetCode" />)
			</xsl:if>
			<xsl:if test="$location!=''">&#160;&#160;&#160;&#160;&#160;&#160;&#160;<span class="fieldLink">
				<a target='_blank'><xsl:attribute name="href"><xsl:value-of select="$location" /></xsl:attribute><xsl:value-of select="$location" /></a>
			</span></xsl:if>
			<xsl:if test="$selectionCriteria='textbox'">&#160;
				<xsl:element name="input">
					<xsl:attribute name="type">text</xsl:attribute>
					<xsl:attribute name="size">1</xsl:attribute>
				</xsl:element>
			</xsl:if>
		</span>
	</xsl:template>
	
	<xsl:template name="createColumns">
		<tr>
			<td width="50%" class="field" align="left">
				<b>Type</b>
			</td>
			<td width="50%" class="field" align="left">
				<b>Status</b>
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="createColumnsForMeds">
		<tr>
			<!--<td width="20%" class="fieldHeader" align="center">Product Name</td>-->
			<td width="50%" class="field" align="center"><b>Product Class</b></td>
			<td width="50%" class="field" align="center"><b>Status for the Medication</b></td>
		</tr>
	</xsl:template>
	<xsl:template name="createColumnsForLabs">
		<xsl:call-template name="createColumns" />
	</xsl:template>

	<xsl:template name="createTypeStatusTable">
		<xsl:param  name="displayNameType" />
		<xsl:param  name="codeSystemNameType" />
		<xsl:param  name="codeType" />
		<xsl:param  name="displayNameStatus"  />
		<xsl:param  name="codeSystemNameStatus"  />
		<xsl:param  name="codeStatus" />
		<tr>
			<td class="field" valign="top">
				<xsl:value-of select="$displayNameType"/>&#160;&#160;(<xsl:value-of select="$codeSystemNameType"/>:&#160;&#160;<xsl:value-of select="$codeType"/>)</td>
			<td class="field" valign="top">
				<xsl:if test="$codeSystemNameStatus!=''">
					<xsl:value-of select="$displayNameStatus"/>&#160;&#160;(<xsl:value-of select="$codeSystemNameStatus"/>:&#160;&#160;
					<xsl:value-of select="$codeStatus"/>)
				</xsl:if>&#160;
			</td>
		</tr>
  </xsl:template>
		
</xsl:stylesheet>
