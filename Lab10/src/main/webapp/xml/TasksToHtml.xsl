<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <title>Tasks</title>
            </head>
            <body style="font-family:Arial;font-size:12pt;background-color:#FCFCFC">
                <xsl:for-each select="tasks/task">
                    <div style="background-color:gray;color:white;padding:4px">
                        <span style="font-weight:bold">
                            <xsl:value-of select="title"/>
                        </span>
                    </div>
                    <div style="margin-bottom:1em;font-size:10pt">
                        <ul>
                            <xsl:for-each select="subtasks/subtask">
                            <li><xsl:value-of select="."/></li>
                            </xsl:for-each>
                        </ul>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>