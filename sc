-- ==========================================================
-- SPEED HUB X - STAND TEXT DETECTOR (INSPECTOR)
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("TextDetectorUI") then
    PlayerGui.TextDetectorUI:Destroy()
end

local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "TextDetectorUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 320, 0, 200)
MainFrame.Position = UDim2.new(0.5, -160, 0.4, -100)
MainFrame.BackgroundColor3 = Color3.fromRGB(18, 18, 24)
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local Corner = Instance.new("UICorner")
Corner.CornerRadius = UDim.new(0, 8)
Corner.Parent = MainFrame

local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, -30, 0, 28)
Title.Position = UDim2.new(0, 10, 0, 0)
Title.BackgroundTransparency = 1
Title.Text = "Stand Text Inspector (Deteksi Papan Nama)"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.SourceSansBold
Title.TextSize = 11
Title.TextXAlignment = Enum.TextXAlignment.Left
Title.Parent = MainFrame

local CloseBtn = Instance.new("TextButton")
CloseBtn.Size = UDim2.new(0, 20, 0, 20)
CloseBtn.Position = UDim2.new(1, -24, 0, 4)
CloseBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
CloseBtn.Text = "X"
CloseBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
CloseBtn.Font = Enum.Font.SourceSansBold
CloseBtn.TextSize = 10
CloseBtn.Parent = MainFrame

CloseBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
end)

local Scroll = Instance.new("ScrollingFrame")
Scroll.Size = UDim2.new(1, -16, 1, -38)
Scroll.Position = UDim2.new(0, 8, 0, 32)
Scroll.BackgroundColor3 = Color3.fromRGB(12, 12, 16)
Scroll.BorderSizePixel = 0
Scroll.ScrollBarThickness = 3
Scroll.ScrollBarImageColor3 = Color3.fromRGB(255, 30, 60)
Scroll.Parent = MainFrame

local Layout = Instance.new("UIListLayout")
Layout.Padding = UDim.new(0, 4)
Layout.SortOrder = Enum.SortOrder.LayoutOrder
Layout.Parent = Scroll

local function AddTextLog(path, textValue)
    local Label = Instance.new("TextLabel")
    Label.Size = UDim2.new(1, -8, 0, 26)
    Label.BackgroundTransparency = 1
    Label.Text = "• " .. textValue .. "\n  [" .. path .. "]"
    Label.TextColor3 = Color3.fromRGB(255, 100, 120)
    Label.Font = Enum.Font.SourceSansSemibold
    Label.TextSize = 10
    Label.TextXAlignment = Enum.TextXAlignment.Left
    Label.Parent = Scroll
end

-- PEMINDAIAN TEKS DI PLOT PULAU
local scriptable = Workspace:FindFirstChild("Scriptable")
local count = 0

if scriptable and scriptable:FindFirstChild("Plots") and scriptable.Plots:FindFirstChild("Buildings") then
    for _, building in ipairs(scriptable.Plots.Buildings:GetChildren()) do
        for _, desc in ipairs(building:GetDescendants()) do
            if desc:IsA("TextLabel") and desc.Text ~= "" then
                count = count + 1
                AddTextLog(desc.Parent.Name, desc.Text)
            end
        end
    end
end

if count == 0 then
    AddTextLog("Workspace", "Tidak ada TextLabel terdeteksi di area Buildings.")
end

Scroll.CanvasSize = UDim2.new(0, 0, 0, Layout.AbsoluteContentSize.Y + 10)
