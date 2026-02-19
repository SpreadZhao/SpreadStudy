package learn

import (
	"context"
	"log"

	"github.com/bytedance/sonic"
	"github.com/cloudwego/eino-ext/components/model/agenticark"
	"github.com/cloudwego/eino-ext/components/model/ollama"
	"github.com/cloudwego/eino/schema"
)

func SimpleArc() {
	ctx := context.Background()

	// 获取 ARK_API_KEY 和 ARK_MODEL_ID: https://www.volcengine.com/docs/82379/1399008
	am, err := agenticark.New(ctx, &agenticark.Config{
		Model:  "doubao-seed-1-8-251228",
		APIKey: "69dc8d01-24b2-4ad0-872c-7388ca9fc2c2",
	})
	if err != nil {
		log.Fatalf("failed to create agentic model, err: %v", err)
	}

	input := []*schema.AgenticMessage{
		schema.UserAgenticMessage("豆包是哪个公司开发的？"),
	}

	msg, err := am.Generate(ctx, input)
	if err != nil {
		log.Fatalf("failed to generate, err: %v", err)
	}

	meta := msg.ResponseMeta.Extension.(*agenticark.ResponseMetaExtension)

	log.Printf("request_id: %s", meta.ID)
	respBody, _ := sonic.MarshalIndent(msg, "  ", "  ")
	log.Printf("  body: %s", string(respBody))
}

func SimpleOllama() {
	ctx := context.Background()
	modelName := "lfm2.5-thinking"

	chatModel, err := ollama.NewChatModel(ctx, &ollama.ChatModelConfig{
		BaseURL: "http://localhost:11434",
		Model:   modelName,
	})
	if err != nil {
		log.Printf("NewChatModel failed, err=%v\n", err)
		return
	}

	resp, err := chatModel.Generate(ctx, []*schema.Message{
		{
			Role:    schema.User,
			Content: "as a machine, how do you answer user's question?",
		},
	})
	if err != nil {
		log.Printf("Generate failed, err=%v\n", err)
		return
	}

	log.Printf("output: \n%v\n", resp)
}
