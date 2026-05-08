package com.example.ai_assistant_service.services;

//import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WeatherClientService {

    private final McpSyncClient mcpSyncClient;

    public WeatherClientService(List<McpSyncClient> mcpSyncClients) {
        if (mcpSyncClients == null || mcpSyncClients.isEmpty()) {
            throw new IllegalStateException("No MCP clients available. Check mcp-servers.json and the server jar path.");
        }
        this.mcpSyncClient = mcpSyncClients.get(0);

        // Debug: print available tools
        this.mcpSyncClient.listTools().tools()
                .forEach(tool -> System.out.println("Registered tool: " + tool.name()));
    }

    public String getCurrentWeather(String city) {
        CallToolResult result = mcpSyncClient.callTool(
                new CallToolRequest(
                        "getWeather",       // exact tool method name
                        Map.of("city", city)       // arguments
                )
        );

        // extract text content from result
        return result.content().stream()
                .filter(c -> c instanceof io.modelcontextprotocol.spec.McpSchema.TextContent)
                .map(c -> ((io.modelcontextprotocol.spec.McpSchema.TextContent) c).text())
                .findFirst()
                .orElse("No response");
    }

    public String getWeatherForecast(String city, int days) {
        CallToolResult result = mcpSyncClient.callTool(
                new CallToolRequest(
                        "getForecast",
                        Map.of("city", city, "days", (Integer) days)
                )
        );

        return result.content().stream()
                .filter(c -> c instanceof io.modelcontextprotocol.spec.McpSchema.TextContent)
                .map(c -> ((io.modelcontextprotocol.spec.McpSchema.TextContent) c).text())
                .findFirst()
                .orElse("No response");
    }
}